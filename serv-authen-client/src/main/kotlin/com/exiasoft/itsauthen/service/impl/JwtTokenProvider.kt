package com.exiasoft.itsauthen.service.impl

import com.exiasoft.itsauthen.config.FunctionListConfig
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.exiasoft.itscommon.util.base64StringToByteArray
import com.exiasoft.itscommon.util.toBase64String
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.*
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset
import java.util.*
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import javax.annotation.PostConstruct

@Component
class JwtTokenProvider(@Value("\${jwt.secret}") val secretKey: String, val functionListConfig: FunctionListConfig): TokenProvider {

    companion object {
        const val KEY_AUTHORITIES = "auth"
        const val KEY_USER_OID = "userOid"
        const val KEY_SESSION_ID = "sessionId"
    }

    private val logger = KotlinLogging.logger {}

    private lateinit var functionShortcutMap: Map<String, String>
    private lateinit var shortcutFunctionMap: Map<String, String>

    @PostConstruct
    fun init() {
        functionShortcutMap = functionListConfig.list.sorted().mapIndexed { index, s -> s to index.toString(36) }.toMap()
        shortcutFunctionMap = functionShortcutMap.map { it.value to it.key }.toMap()
    }

    override fun createToken(authentication: Authentication, groupIds: Set<String>?, claims: Map<String, String>?, lifetime: Long): AuthenticationToken {

        val now = Date().time
        val validity = Date(now + lifetime)

        val authorities = groupIds ?: authentication.authorities.map { it.authority }
        val authoritiesStr = authorities.map { it -> functionShortcutMap[it] ?: it }.joinToString(separator = "|")
        val baos = ByteArrayOutputStream()
        GZIPOutputStream(baos).bufferedWriter().use { it.write(authoritiesStr) }
        val content = baos.toByteArray().toBase64String()
        baos.close()

        val c = ((claims ?: emptyMap()) as Map<String, Any>).toMutableMap()
        return JwtToken("Bearer ${Jwts.builder()
                .setClaims(c)
                .setSubject(authentication.name)
                .claim(KEY_AUTHORITIES, content)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(validity)
                .compact()}")
    }

    override fun createToken(tokenStr: String): AuthenticationToken {
        return JwtToken(tokenStr)
    }

    inner class JwtToken(@JsonProperty("id_token") override var idToken: String) : AuthenticationToken {
        private val logger = this@JwtTokenProvider.logger

        override fun validate(): Boolean {
            if (idToken.isBlank()) return false
            try {
                Jwts.parser().setSigningKey(this@JwtTokenProvider.secretKey).parseClaimsJws(getToken())
                return true
            } catch (e: SignatureException) {
                logger.error("Invalid JWT signature.", e)
            } catch (e: MalformedJwtException) {
                logger.error("Invalid JWT token.", e)
            } catch (e: ExpiredJwtException) {
                logger.error("Expired JWT token.", e)
            } catch (e: UnsupportedJwtException) {
                logger.error("Unsupported JWT token.", e)
            } catch (e: IllegalArgumentException) {
                logger.error("JWT token compact of handler are invalid.", e)
            }
            return false
        }

        override fun getAuthentication(): Authentication? {
            val claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(getToken())
                    .body
            val content = claims[KEY_AUTHORITIES].toString()
            val authoritiesStr = if (content.isEmpty()) content else GZIPInputStream(content.base64StringToByteArray().inputStream()).bufferedReader().use { it.readText() }
            val authorities = authoritiesStr.split("|").dropLastWhile { it.isEmpty() }.map { it ->
                SimpleGrantedAuthority(shortcutFunctionMap[it] ?: it)
            }
            val principal = User(claims.subject ?: "", "", authorities)
            return UsernamePasswordAuthenticationToken(principal, idToken, authorities)
        }

        override fun getUserOid(): String? {
            val claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(getToken())
                    .body
            return claims[KEY_USER_OID]?.toString()
        }

        private fun getToken(): String? {
            return if (idToken.startsWith("Bearer ")) idToken.substring(7) else idToken
        }

        override fun toString(): String {
            return if (idToken.startsWith("Bearer ")) idToken else "Bearer $idToken"
        }
    }
}