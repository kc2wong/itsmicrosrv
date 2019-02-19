# itsmicrosrv


This project demonstrates a cloud native microservice application.  It is a spring boot application written in Kotlin, with the following features demonstrated :
 
  - Spring WebFlux
  - Spring Security
  - Spring Cloud Config
  - Spring Cloud Discovery
  - Spring Cloud Zuul
  - Spring Cloud Sleuth
  - Spring Rest Docs and Spring Rest AutoDocs (https://scacap.github.io/spring-auto-restdocs/)

---
  
## Application Architecture

![](doc/architecture.png)


### [Gateway Service](https://github.com/kc2wong/itsmicrosrv/tree/master/serv-gateway)

Serves as the single entry point of in the system.  It is built with spring cloud gateway with customization on security setting, CORS setting and web filter

### [Discovery Service](https://github.com/kc2wong/itsmicrosrv/tree/master/serv-discovery)

Registry service for load balancing and failover.  It is built with spring cloud eureka discovery without additional code

### [Config Service](https://github.com/kc2wong/itsmicrosrv/tree/master/serv-config)

Centralized configuration in the system.  It is built with spring cloud config server without additional code.  YAML files are used to store the configuration

### [Authentication Service](https://github.com/kc2wong/itsmicrosrv/tree/master/serv-authen)

Provides user authentication.  After a user with authentication, a JWT will be returned in the header.  Subsequent requests sent from client needs to include the JWT as Bearer token

### [Static Data Service](https://github.com/kc2wong/itsmicrosrv/tree/master/serv-staticdata)

Provides static data retrieval functions.  The provided service will be also consumed by other services 

### [Account Service](https://github.com/kc2wong/itsmicrosrv/tree/master/serv-account)

Provides account information retrieval functions. 

### [Order Service](https://github.com/kc2wong/itsmicrosrv/tree/master/serv-order)

Provides order retrieval functions. Experience API pattern is demonstrated

### [Market Data Service](https://github.com/kc2wong/itsmicrosrv/tree/master/serv-marketdata)

Retrieved stock quotes from Yahoo Finance

### [Its Monolith](https://github.com/kc2wong/itsmicrosrv/tree/master/its-monolith)

Its Monolith is not a microservice.  In stead it combines all the microservices to form a monolithlic application

### [Zipkin Service](https://github.com/kc2wong/itsmicrosrv/tree/master/serv-zipkin)

_Optional_ -- Collects trace messages from all the microservices

---

### Quick Start

#### Prerequisites

* JDK 8 / 10

After download the source codes, execute the following commands to start all required microservices (Assumes in Windows environment, open a command prompt for each command)
```
$ cd itsmicrosrv
$ gradlew itsdiscovery:bootRun
$ gradlew itsconfig:bootRun

## Wait until the above two service are started

$ gradlew itsauthen:bootRun
$ gradlew itsstaticdata:bootRun
$ gradlew itsaccount:bootRun
$ gradlew itsorder:bootRun
$ gradlew itsmarketdata:bootRun
$ gradlew itsgateway:bootRun

## Wait until the above services are started

```

Verification
```
curl -H "Content-Type:application/json" -X POST -d "{\"userid\": \"AD2923702\"}" http://localhost:8080/authentication/int/v1/papi/authen-token

## A Bearer token will be returned
Bearer eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyT2lkIjoiNjAwMDEwMDMwMyIsInN1YiI6IkFEMjkyMzcwMiIsImF1dGgiOiJINHNJQUFBQUFBQUFBQTJVaHhIRElBQURWeklkeGdIY2U3ZVQrK0dURFhUUzZ3c0VFb1hHWUhGNEFwRkVwcVNpcHFHbG8yZGdaR0ptWVdWajUrRGs0dWJoNWNNWFVTQUVRaUlVUWlNTXdpSWN3aU1DSWlJU0lpTktSSVdvRVEyaVJYU0lIakVnUnNTRW1CRUxZa1ZzaUIxeElFN0VoYmdSRCtKRmZCQmZaSUg4cDVaSWhkUklnN1JJaC9USWdJekloTXpJRWxraGEyU0RiSkVkc2tjT3lCRTVJV2ZrZ2x5UkczSkhIc2dUZVNGdjVJTjhrUi9rRjFXZ0JPcGZrVUpwbEVGWmxFTjVWRUJGVkVKbFZJbXFVRFdxUWJXb0R0V2pCdFNJbWxBemFrR3RxQTIxb3c3VWlicFFOK3BCdmFnUDZvc3UwQUl0MGY4OU5OcWdMZHFoUFRxZ0l6cWhNN3BFVitnYTNhQmJkSWZ1MFFONlJFL29HYjJnVi9TRzN0RUgra1JmNkJ2OW9GLzBCLzNGRkJpQmtSaUYrWTl2TUJiak1CNFRNQkdUTUJsVFlpcE1qV2t3TGFiRDlKZ0JNMkltekl4Wk1DdG13K3lZQTNOaUxzeU5lVEF2NW9QNVlndXN3RXFzd21yc256U0xkVmlQRGRpSVRkaU1MYkVWdHNZMjJCYmJZWHZzZ0IyeEUzYkdMdGdWdTJGMzdJRTlzUmYyeGo3WUYvdkJmbkVGVHVBa1R1RTB6dUQrV0R1Y3h3VmN4Q1ZjeHBXNENsZmpHbHlMNjNBOWJzQ051QWszNHhiY2l0dHdPKzdBbmJnTGQrTWUzSXY3NEw3NEFpL3dFcS93R20vd0Z2Ly9rTWNIZk1RbmZNYVgrQXBmNHh0OGkrL3dQWDdBai9nSlArTVgvSXJmOER2K3dKLzRDMy9qSC95TC8rQy9oSUlnQ0pLZ0NKcGdDSmJnQ1AvREJrSWtKRUltbElTS1VCTWFRa3ZvQ0QxaElJeUVpVEFURnNKSzJBZzc0U0NjaEl0d0V4N0NTL2dRdnNTQ0tJaVNxSWlhYUlpVzZJaWUrTGRESkNaaUpwYkVpbGdURzJKTDdJZzljU0NPeElrNEV4ZmlTdHlJTy9FZ25zU0xlQk1mNGt2OEVMK2tnaVJJa3FSSW1tUklsdVJJbmhSSWZ4VWxVaWFWcElwVWt4cFNTK3BJUFdrZ2phU0pOSk1XMGtyYVNEdnBJSjJraTNTVEh0SkwrcEMrNUlJc3lKS3N5SnBzeUpic3lKNGN5Skg4OTE0bWwrU0tYSk1iY2t2dXlEMTVJSS9raVR5VEYvSkszc2c3K1NDZjVPc0huREg5OEVnRkFBQT0iLCJleHAiOjE1NTAyNDAwMDF9.ktIN_-CVBk7HkGhugy5GC411IuPggsOx-XAFTWEQTc4bTaCjqrMMm3V9v3FcK4fUVOMaeJvc6bMbG0TyQhltOw
```

Consumes other microservices
```
## Replace the Bearer token by result of above call
curl -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyT2lkIjoiNjAwMDEwMDMwMyIsInN1YiI6IkFEMjkyMzcwMiIsImF1dGgiOiJINHNJQUFBQUFBQUFBQTJVaHhIRElBQURWeklkeGdIY2U3ZVQrK0dURFhUUzZ3c0VFb1hHWUhGNEFwRkVwcVNpcHFHbG8yZGdaR0ptWVdWajUrRGs0dWJoNWNNWFVTQUVRaUlVUWlNTXdpSWN3aU1DSWlJU0lpTktSSVdvRVEyaVJYU0lIakVnUnNTRW1CRUxZa1ZzaUIxeElFN0VoYmdSRCtKRmZCQmZaSUg4cDVaSWhkUklnN1JJaC9USWdJekloTXpJRWxraGEyU0RiSkVkc2tjT3lCRTVJV2ZrZ2x5UkczSkhIc2dUZVNGdjVJTjhrUi9rRjFXZ0JPcGZrVUpwbEVGWmxFTjVWRUJGVkVKbFZJbXFVRFdxUWJXb0R0V2pCdFNJbWxBemFrR3RxQTIxb3c3VWlicFFOK3BCdmFnUDZvc3UwQUl0MGY4OU5OcWdMZHFoUFRxZ0l6cWhNN3BFVitnYTNhQmJkSWZ1MFFONlJFL29HYjJnVi9TRzN0RUgra1JmNkJ2OW9GLzBCLzNGRkJpQmtSaUYrWTl2TUJiak1CNFRNQkdUTUJsVFlpcE1qV2t3TGFiRDlKZ0JNMkltekl4Wk1DdG13K3lZQTNOaUxzeU5lVEF2NW9QNVlndXN3RXFzd21yc256U0xkVmlQRGRpSVRkaU1MYkVWdHNZMjJCYmJZWHZzZ0IyeEUzYkdMdGdWdTJGMzdJRTlzUmYyeGo3WUYvdkJmbkVGVHVBa1R1RTB6dUQrV0R1Y3h3VmN4Q1ZjeHBXNENsZmpHbHlMNjNBOWJzQ051QWszNHhiY2l0dHdPKzdBbmJnTGQrTWUzSXY3NEw3NEFpL3dFcS93R20vd0Z2Ly9rTWNIZk1RbmZNYVgrQXBmNHh0OGkrL3dQWDdBai9nSlArTVgvSXJmOER2K3dKLzRDMy9qSC95TC8rQy9oSUlnQ0pLZ0NKcGdDSmJnQ1AvREJrSWtKRUltbElTS1VCTWFRa3ZvQ0QxaElJeUVpVEFURnNKSzJBZzc0U0NjaEl0d0V4N0NTL2dRdnNTQ0tJaVNxSWlhYUlpVzZJaWUrTGRESkNaaUpwYkVpbGdURzJKTDdJZzljU0NPeElrNEV4ZmlTdHlJTy9FZ25zU0xlQk1mNGt2OEVMK2tnaVJJa3FSSW1tUklsdVJJbmhSSWZ4VWxVaWFWcElwVWt4cFNTK3BJUFdrZ2phU0pOSk1XMGtyYVNEdnBJSjJraTNTVEh0SkwrcEMrNUlJc3lKS3N5SnBzeUpic3lKNGN5Skg4OTE0bWwrU0tYSk1iY2t2dXlEMTVJSS9raVR5VEYvSkszc2c3K1NDZjVPc0huREg5OEVnRkFBQT0iLCJleHAiOjE1NTAyNDAwMDF9.ktIN_-CVBk7HkGhugy5GC411IuPggsOx-XAFTWEQTc4bTaCjqrMMm3V9v3FcK4fUVOMaeJvc6bMbG0TyQhltOw" -X GET http://localhost:8080/staticdata/v1/sapi/currencies/HKD

## The following json object will be returned
{"currencyCode":"HKD","isoCode":"HKD","decimalPoint":2,"descptDefLang":"Hong Kong Dollar","descpt2ndLang":"港幣,"descpt3rdLang":"港币","unitName":"Dollar","subUnitName":"Cent","lastModUser":"SYSADMALL","lastModTimestamp":"2011-11-19 09:17:46","syncstr":"2000/01/01 00:00:00","status":"A","version":1}```
```

Integration with consumer app <br>
Refer to [itswebui](https://github.com/kc2wong/itswebui)

---

### High level patterns and conventions in API and microservices
- API Category
  - System API (sapi)
    - CRUD operation
    - Enquiry function
  - Process API (papi)
    - Transactional operation that involves business logic or computation
    - Consumes downstream fulfilment system transaction
  - Experience API (xapi)
    - Tailor made for a particular screen, usually consumes multiple APIs and combined the results into one messages
- API naming convention
  - External API
    - An external api is to be consumed by GUI and other third party consumers 
    - **service-name**/**version**/**api-category**/**resource-names**
  - Internal API
    - An internal api is to be consumed by other microservices within the same domain 
    - **service-name**/**int**/**version**/**api-category**/**resource-names**
  - Resource name should be noun in plural form, avoid using verbs if possible, e.g. /interest-calculation but not /calculate-interest
  - In case verb is used, the resource should accept only one http method, e.g. if /calculate-interest is used, it should only be a http POST only
  - Resource path and query parameters should not contain sensitive data in plain form, such as account number and name.  The value should be encrypted, see [Authentication Service](https://github.com/kc2wong/itsmicrosrv/tree/master/serv-authen) for more detail

- Model
  - Models are data class which maps to a data source
  - Internal ID (sequence or generated uuid) is used as primary key
  - When there are child objects under the model, consider define a SimpleXXX object
    - SimpleXXX is an interface that represents the fields in parent object.  A SimpleXXX interface is for enquiry functions
    - SimpleXXXData is a **data class** that implements the SimpleXXX interface, without any child object
    - XXX is a **data class** that also implements the SimpleXXX interface and with child objects.  XXX is for locating an single (or limited number) of result.  E.g, SimpleExchange, SimpleExchangeData and Exchange
  - Models are returned in internal API

- DTO
  - DTOs are data class to be returned to external API consumers.  DTOs maps to a single model or aggregation of models
  - DTO does not contain any internal id.  Internal ID are translated to meaning value (e.g. code)
  - DTOs are also divided into XXXDto and SimpleXXXDto.  However, there is no need to define SimpleXXX interface as there is no need to maintain class hierarchy

- Data Types
  - Both model and dto are serialized to json objects in return value, with following primitive data types
    - String
    - Number
    - Boolean (Converted to Y or N but not true or false)
    - Date without timezone (Converted to YYYY-MM-DD string format)
    - DateTime without timezone (Converted to YYYY-MM-DD HH:MI:SS string format)
    - Enumeration - Convert to string value.  However, the string should be descriptive word for DTO and can be code for model.  E.g, For status field, it is serialized to "Active" in DTO and "A" in model

- Mapper
  - Mapper are spring components that map between Models and DTOs (bi-directional)
  - To facilitate the conversion, the field names in Model and corresponding DTO should be the same
  
- Return value of API (Restful controller and underlying services)
  - Must be Flux or Mono
  - Internal API consumers must use webclient to send http request

- Return Status of API
  - 200 Success
    - A GET / POST (without resource created) / PUT / DELETE request is executed successfully
    - If operation is executed successfully but with warning, the warning code **SHOULD BE** included in response payload but not header
  - 201 Created
    - A POST request with record created successfully
    - If operation is executed successfully but with warning, the warning code **SHOULD BE** included in response payload but not header
  - 400 Bad request
    - Exception occurred
    - Error code and (optional) error parameter should be returned in header
  - 401 Unauthorized
    - User is not authenticated
    - User does not have required access right to consume the API
    - User does not have required entitlement to access the found resource (e.g. particular account)
  - 500 Not found
    - When resource path is invalid
    - When search record by unique key but there is no matched result
  - And more....
    
- Spring components
  - @RestController are used to define the end point of API.  The controllers are under "resource" package
  - External and internal APIs are defined in separate controllers
  - Backing service should be defined as interfaces, with @Service implementation class

### Message Logging
  - Spring cloud sleuth is used to generate correlation id and sent to zippkin server
  - Follow the steps below to enable message tracing with zippkin 
```
$ cd itsmicrosrv\serv-zipkin
$ gradlew bootRun
```

  - Log messages can also be sent to logging server such as Splunk or "Elasticsearch, Logstash, and Kibana" stack (aka ELK)
  - Setup of Splunk or ELK is out of the scope of this project.  Details can be found from other resources
  - After ELK is stared, modify the logback.xml in required services and uncomment the following section (Modify the host ip address)
```
    <appender name="GELF" class="de.siegmar.logbackgelf.GelfUdpAppender">
    <graylogHost>192.168.11.37</graylogHost>
    <graylogPort>12201</graylogPort>
    <layout class="de.siegmar.logbackgelf.GelfLayout">
    <shortPatternLayout class="ch.qos.logback.classic.PatternLayout">
    <pattern>${SLEUTH_PATTERN}</pattern>
    </shortPatternLayout>
    <fullPatternLayout class="ch.qos.logback.classic.PatternLayout">
    <pattern>${SLEUTH_PATTERN}</pattern>
    </fullPatternLayout>
    </layout>
    </appender>
```
  - Then add the appender to appender "GELF" to required loggers

### API Documentation

Spring Rest Docs and Spring Rest AutoDocs are used to generate API documentation.  Spring Rest Docs is a test driven documentation framework and therefore it is expected to have a separated set of test cases and mock results for generation of API documentation

  - Follow the steps below to generate sample API documentation for static data service
```
$ gradlew itsstaticdata:asciidoctor
```

Result will be generated in directory itsmicrosrv\serv-staticdata\build\asciidoc\html5.  Sample generated API documentation can be found in [here](http://htmlpreview.github.io/?https://github.com/kc2wong/itsmicrosrv/blob/master/serv-staticdata/doc/api/index.html)

Spring Rest Docs is handy for API documentation generation.  However, for enterprise with a lot of APIs built, it is always required to maintain a API inventory and therefore it is more desirable to generate documentation that meet specific standard, e.g. OpenAPI or RAML

Yet to find out how to generate multiple formats of API documentation

### Next Steps

* Deployment with Docker and Kubernetes
* Deployment to AWS
* Error reporting and handling in microservices
* Retrieve data from database with r2dbc

