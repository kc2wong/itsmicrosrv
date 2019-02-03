package com.exiasoft.itsstaticdata.service

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.model.enumaration.Status
import com.exiasoft.itscommon.service.BaseServiceIntf
import com.exiasoft.itsstaticdata.model.Instrument
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class InstrumentService(val simeplStockService: SimpleStockService): BaseServiceIntf<Instrument.Id, Instrument> {

    fun find(authenToken: AuthenticationToken, exchangeBoardOid: Set<String>?, instrumentCode: String?, nameDefLang: String?, status: Status?, pageable: Pageable): Mono<Page<Instrument>> {
        return simeplStockService.find(authenToken, exchangeBoardOid, instrumentCode, nameDefLang, status, pageable).map {
            PageImpl(it.content.asSequence().map { e -> e as Instrument }.toList(), it.pageable, it.totalElements)
        }
    }

    override fun findAll(authenToken: AuthenticationToken, pageable: Pageable): Mono<Page<Instrument>> {
        return simeplStockService.findAll(authenToken, pageable).map { p ->
            PageImpl(p.content.asSequence().map { it as Instrument }.toList())
        }
    }

    override fun findByIdentifier(authenToken: AuthenticationToken, id: Instrument.Id): Mono<Instrument> {
        return simeplStockService.findByIdentifier(authenToken, id).map {
            it as Instrument
        }
    }

    override fun findByOid(authenToken: AuthenticationToken, oid: String): Mono<Instrument> {
        return simeplStockService.findByOid(authenToken, oid).map {
            it as Instrument
        }
    }

    override fun findByOids(authenToken: AuthenticationToken, oids: Set<String>): Mono<Map<String, Instrument>> {
        return simeplStockService.findByOids(authenToken, oids).map {
            it.map { it.key to it.value as Instrument }.toMap()
        }
    }
}