# itsaccount

itsaccount server component provides the following microservices (dummy data):
  - Trading accounts retrieval
    - Returns two accounts if login user is AD2923702 and none otherwise 
  - Portfolio retrieval
 
itsaccount also consumes the internal APIs provided by itsauthen and itsstaticdata.  The client implementation of the internal APIs acquires ip address and port number of the downstream services from itsdiscovery, sends http request to service provider and deserializes the result json back to Kotlin objects

In addition, itsaccount also demonstrates the experience API pattern.  In portfolio enquiry page of the web application.  Normally it needs to consumes the following 2+ APIs in order to render the page:
1. Retrieve the account portfolio from itsaccount
2. Retrieve the instruments in portfolio from itstaticdata, one call for an exchange

An experience API is built to combine the above two in one single API, in order to save the number of API calls required.  This is helpful in the case for mobile application where internet connection could be slower and unstable.<br>

Experience API is implemented in resource controller but not defined in the service API interface.

```
    @GetMapping("/xapi/trading-accounts/{tradingAccountCode}/currencies/{currencyCode}/portfolios")
    fun getAccountPortfolio(authenToken: AuthenticationToken,
            @PathVariable tradingAccountCode: String,
            @PathVariable currencyCode: String
    ): Mono<ResponseEntity<AccountPortfolioBundle>> {
        logger.trace("TradingAccountController.getAccountPortfolio() starts, tradingAccountCode = ${tradingAccountCode} , currencyCode = ${currencyCode} ")

        return portfolioEnquiryService.findByIdentifier(authenToken, AccountPortfolio.AccountPortfolioId(tradingAccountCode, currencyCode)).flatMap { acctPortfolio ->
            // convert securities position to map of <ExchangeOid, List of Instrument Code>
            val instrumentCodeMap = acctPortfolio.securityPositionSummary.groupBy { it.exchangeOid }.mapValues { it.value.map { it.instrumentCode } }
            simpleExchangeService.findAll(authenToken, Pageable.unpaged()).flatMap {
                // Consume get instrument API (once per Exchange with instrument in portfolio) and merge the result with portfolio
                val requestParams = it.content.map {
                    mapOf("exchangeCode" to it.exchangeCode, "instrumentCode" to (instrumentCodeMap[it.exchangeOid]?.let{it.joinToString(",")}  ?: "" ))
                }.filter { it.values.isNotEmpty() }.toList()
                // Call external API in itsstaticdata to get instruments
                consumeResourceAndCollect(authenToken, itsstaticdataAppName, "/staticdata/v1/sapi/instruments", requestParams).flatMap { extResource ->
                    val portfolio = portfolioMapper.modelToDto(authenToken, acctPortfolio) { AccountPortfolioBundle(it, extResource.asSequence().toList())}
                    WebResponseUtil.wrapOrNotFound(portfolio!!)
                }
            }
        }
    }

    data class AccountPortfolioBundle(val accountPortfolio: AccountPortfolioDto, val instruments: List<Any>)

``` 

The class AccountPortfolioBundle combines the portfolio and related instruments and return as a whole object.  List<`Any`> is used to represent a list of InstrumentDto (json) <br>

After retrieving the portfolio, it triggers and **internal** api to get all exchanges.  For exch exchange with instrument in portfolio, it triggers an **external** API to get instruments (Dto) in the exchange, then combine the result and result to consumer