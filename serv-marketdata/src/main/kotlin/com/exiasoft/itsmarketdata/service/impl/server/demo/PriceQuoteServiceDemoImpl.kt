package com.exiasoft.itsmarketdata.service.impl.server.demo

import com.exiasoft.itscommon.authen.AuthenticationToken
import com.exiasoft.itscommon.util.DateTimeUtil
import com.exiasoft.itsmarketdata.model.PriceQuote
import com.exiasoft.itsmarketdata.service.PriceQuoteService
import com.exiasoft.itsstaticdata.model.Instrument
import com.exiasoft.itsstaticdata.service.ExchangeBoardService
import com.exiasoft.itsstaticdata.service.InstrumentService
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import yahoofinance.YahooFinance
import java.io.IOException
import java.math.BigDecimal

@Service
class PriceQuoteServiceYahooFinanceImpl(val instrumentService: InstrumentService, val exchangeBoardService: ExchangeBoardService) : PriceQuoteService {

    private val logger = KotlinLogging.logger {}

    override fun findByIdentifier(authenToken: AuthenticationToken, id: Instrument.Id): Mono<PriceQuote> {
        val symbol = String.format("%s.HK", if (id.instrumentCode.length == 5) id.instrumentCode.substring(1) else id.instrumentCode)
        logger.debug { "Yahoo symbol of ${id.instrumentCode} = $symbol" }
        try {
            val instrumentResult = instrumentService.findByIdentifier(authenToken, id)
            val exchBoardResult = exchangeBoardService.findByOid(authenToken, id.exchangeBoardOid)
            return instrumentResult.zipWith(exchBoardResult).flatMap {
                val instrument = it.t1
                val exchangeBoard = it.t2

                YahooFinance.get(symbol)?.quote?.let {
                    Mono.just(PriceQuote(exchangeBoard!!.exchangeOid ?: "", id.instrumentCode, instrument?.tradingCurrencyOid ?: "",
                            instrument?.shortNameDefLang ?: "", instrument?.shortName2ndLang, instrument?.shortName3rdLang,
                            it.price, it.previousClose, it.change, it.changeInPercent, it.bid, it.ask,
                            it.dayHigh, it.dayLow, it.yearHigh, it.yearLow, BigDecimal.valueOf(it.volume), DateTimeUtil.getCurrentDateTime()))
                }
            }
        } catch (ioe: IOException) {
            logger.error("error in obtain price code for $symbol", ioe)
        }
        return Mono.empty()
    }
}