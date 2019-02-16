# itsstaticdata

itsstaticdata provides retrieval of static data such as Exchange / Instrument / Order Type...etc .  It is divided in to [client](https://github.com/kc2wong/itsmicrosrv/tree/master/serv-staticdata-client) and server components

### Client component
When a microservice include itsauthen-client.jar in classpath, the following functions can be used
- A client implementation of _DataEntitlementService_ that sends a http call to itsstaticdata internal microservice
- Auto configuration is used to config the above features

### Server component
itsstaticdata server component provides the following microservices (dummy data with limited filtering capability ):
  - Currency retrieval 
  - Exchange retrieval
  - Exchange Board retrieval
  - Instrument retrieval
    - Stock code 00001 to 00010, 00016 and 00023 for HKG Exchange
    - Stock code 600000 for MAMK Exchange    
  - Operation Unit retrieval
  - Trading Account Type retrieval

  
For complex objects, it should be divided into "Normal" and "Simple" objects where simple version only contains top level attributes for general searching and enquiry purpose and normal version is for maintenance and search with limited result<br>
Enquiry on normal objects with potentially large number of matched result should not be exposed as external API

A few junit test cases are written in order to generate spring rest doc API.  The test case execution including request and response will be captured by spring auto restdoc to generate the document<br>

Format of API doc can be customized by modifying the template in this [directory](src\docs\asciidoc)
 
  