package com.exiasoft.itsstaticdata.config

import com.exiasoft.itsstaticdata.service.InstrumentService
import com.exiasoft.itsstaticdata.service.impl.client.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@ConditionalOnClass(WebFluxConfigurer::class, LoadBalancerClient::class)
class ItsStaticDataServiceClientAutoConfig(@Value("\${itsstaticdata.application.name:itsstaticdata}") val appName: String, val loadBalancingClient: LoadBalancerClient, val webClientBuilder: WebClient.Builder) {

    @Bean
    @ConditionalOnMissingBean(name = ["CurrencyServiceImpl"])
    fun currencyServiceClient() : CurrencyServiceImpl {
        return CurrencyServiceImpl(appName, loadBalancingClient, webClientBuilder)
    }

    @Bean
    @ConditionalOnMissingBean(name = ["ExchangeBoardPriceSpreadServiceImpl"])
    fun exchangeBoardPriceSpreadServiceClient() : ExchangeBoardPriceSpreadServiceImpl {
        return ExchangeBoardPriceSpreadServiceImpl(appName, loadBalancingClient, webClientBuilder)
    }

    @Bean
    @ConditionalOnMissingBean(name = ["ExchangeBoardServiceImpl"])
    fun exchangeBoardServiceClient() : ExchangeBoardServiceImpl {
        return ExchangeBoardServiceImpl(appName, loadBalancingClient, webClientBuilder)
    }

    @Bean
    @ConditionalOnMissingBean(name = ["ExchangeServiceImpl"])
    fun exchangeServiceClient() : ExchangeServiceImpl {
        return ExchangeServiceImpl(appName, loadBalancingClient, webClientBuilder)
    }

    @Bean
    @ConditionalOnMissingBean(name = ["SimpleOperationUnitServiceImpl"])
    fun simpleOperationUnitServiceClient() : SimpleOperationUnitServiceImpl {
        return SimpleOperationUnitServiceImpl(appName, loadBalancingClient, webClientBuilder)
    }

    @Bean
    @ConditionalOnMissingBean(name = ["SimpleStockServiceImpl"])
    fun instrumentServiceClient() : InstrumentService {
        val simpleStockService = SimpleStockServiceImpl(appName, loadBalancingClient, webClientBuilder)
        return InstrumentService(simpleStockService)
    }

    @Bean
    @ConditionalOnMissingBean(name = ["TradingAccountTypeServiceImpl"])
    fun tradingAccountTypeServiceClient() : TradingAccountTypeServiceImpl {
        return TradingAccountTypeServiceImpl(appName, loadBalancingClient, webClientBuilder)
    }

}