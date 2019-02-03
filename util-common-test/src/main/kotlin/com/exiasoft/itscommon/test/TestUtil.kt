package com.exiasoft.itscommon.test

import capital.scalable.restdocs.AutoDocumentation
import capital.scalable.restdocs.AutoDocumentation.responseFields
import capital.scalable.restdocs.jackson.FieldDescriptors
import capital.scalable.restdocs.payload.JacksonResponseFieldSnippet
import capital.scalable.restdocs.request.RequestParametersSnippet
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors.limitJsonArrayLength
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors.replaceBinaryContent
import capital.scalable.restdocs.webflux.WebTestClientInitializer
import com.exiasoft.itscommon.annotation.PageableDefault
import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.authen.TokenProvider
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.ApplicationContext
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.cli.CliDocumentation
import org.springframework.restdocs.http.HttpDocumentation
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.snippet.Snippet
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document
import org.springframework.restdocs.webtestclient.WebTestClientSnippetConfigurer
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.test.web.reactive.server.ExchangeResult
import org.springframework.web.method.HandlerMethod
import java.util.function.Consumer

class TestUtil {

    companion object {

        fun createToken(tokenProvider: TokenProvider, functionCodeList: List<String>) : AuthenticationToken {
            return tokenProvider.createToken(UsernamePasswordAuthenticationToken("testuser", "", functionCodeList.map { GrantedAuthority { it } }),
                    functionCodeList.toSet(), emptyMap(), 1000L * 60)

        }

        fun defaultRestDocument(applicationContext: ApplicationContext, restDocumentation: RestDocumentationContextProvider): WebTestClientSnippetConfigurer {
            val requestParametersSnippet = object: RequestParametersSnippet() {
                override fun getTranslationKeys(): Array<String> {
                    return super.getTranslationKeys().plus(arrayOf("th-paging-parameter", "th-paging-value", "pagination-request-paging"))
                }

                override fun enrichModel(model: MutableMap<String, Any>?, handlerMethod: HandlerMethod?, fieldDescriptors: FieldDescriptors?) {
                    super.enrichModel(model, handlerMethod, fieldDescriptors)
                    model?.let {
                        model["isPagingDefault"] = false
                        if (model["isPageRequest"] == true) {
                            handlerMethod?.let { mth ->
                                mth.methodParameters.mapNotNull { mp -> mp.getParameterAnnotation(PageableDefault::class.java) }.forEach {
                                    model["isPagingDefault"] = true
                                    model["pagingDefaultContent"] = arrayOf(
                                            mapOf("parameter" to "page", "value" to it.page),
                                            mapOf("parameter" to "size", "value" to if (it.size > 0) it.size else "Defined in application config \"its.page.size\"" ),
                                            mapOf("parameter" to "sort", "value" to it.sort.joinToString(","))
                                    )
                                }
                            }
                        }
                    }
                }
            }

            val responseFieldSnippet = object: JacksonResponseFieldSnippet() {
                override fun enrichModel(model: MutableMap<String, Any>?, handlerMethod: HandlerMethod?, fieldDescriptors: FieldDescriptors?) {
                    super.enrichModel(model, handlerMethod, fieldDescriptors)
                }
            }

            return WebTestClientRestDocumentation.documentationConfiguration(restDocumentation).snippets()
                    .withDefaults(WebTestClientInitializer.prepareSnippets(applicationContext),
                            CliDocumentation.curlRequest(),
                            HttpDocumentation.httpRequest(),
                            HttpDocumentation.httpResponse(),
                            responseFieldSnippet,
                            responseFields(),
                            AutoDocumentation.pathParameters(),
                            requestParametersSnippet,
                            AutoDocumentation.description(),
                            AutoDocumentation.methodAndPath(),
                            AutoDocumentation.section(),
                            authorization()
                    )
        }

        fun <T : ExchangeResult> basicResponseDoc(objectMapper: ObjectMapper, clazz: Class<*>? = null) : Consumer<T> {
            return if (clazz == null) {
                document("{class-name}/{method-name}", Preprocessors.preprocessRequest(Preprocessors.prettyPrint()), commonResponsePreprocessor(objectMapper))
            } else  {
                document("{class-name}/{method-name}", Preprocessors.preprocessRequest(Preprocessors.prettyPrint()), commonResponsePreprocessor(objectMapper), responseFields().responseBodyAsType(clazz))
            }
        }

        fun <T : ExchangeResult> pageResponseDoc(objectMapper: ObjectMapper, clazz: Class<*>) : Consumer<T> {
            val pageResponseFieldSnippet = object: JacksonResponseFieldSnippet(clazz, false) {
                override fun enrichModel(model: MutableMap<String, Any>?, handlerMethod: HandlerMethod?, fieldDescriptors: FieldDescriptors?) {
                    super.enrichModel(model, handlerMethod, fieldDescriptors)
                    model?.let {
                        it["isPageResponse"] = true
                    }
                }
            }
            return document("{class-name}/{method-name}", Preprocessors.preprocessRequest(Preprocessors.prettyPrint()), commonResponsePreprocessor(objectMapper), pageResponseFieldSnippet)
        }

        private fun commonResponsePreprocessor(objectMapper: ObjectMapper): OperationResponsePreprocessor {
            return preprocessResponse(replaceBinaryContent(),
                    limitJsonArrayLength(objectMapper), prettyPrint())
        }

        fun authorization() : Snippet {
            return AutoDocumentation.authorization("Required JWT")
        }

    }

}