# itsauthen

itsauthen provides authentication service and data entitlement service.  It is divided into [client](https://github.com/kc2wong/itsmicrosrv/tree/master/serv-authen-client) and server components

### Client component
When a microservice includes itsauthen-client.jar in classpath, the following functions can be used
- An permission evaluator for other microservices to pre-author a restful APIs
  - E.g. in OrderResource.kt, @PreAuthorize("@permissionEvaluator.hasPermission(authentication, '{EnquiryGrp.Order.Enquiry}')")
  - The above annotation will check if the request is authorized with function 'EnquiryGrp.Order.Enquiry', error 401 will be returned if not authorized
- A argument resolver to inject AuthenToken (JWT) to method argument  
- A client implementation of _DataEntitlementService_ that sends a http call to itsauthen microservice to get data entitlement of current login user
- Auto configuration is used to config the above features

### Server component
itsauthen server component provides the following functions
- Microservice to authenticate a user.  A two step proprietary algorithm is adopted. 
  - The service does not actually implement any logic, it only reads dummy data from resource file under directory resources/service/demo
  - After an user is authenticated, a JWT with following claims will be created
    - userOid - Internal id of the authenticated user
    - sub - Userid of the authenticated user
    - auth - List of entitled function code concatenated to a single string, then compressed to a hex string.  In order to shorten the length, the function code is converted to a number according to sequence defined in application.yml and therefore the order cannot be changed
  - For simplicity, the secret key to sign the JWT is hard coded in configuration file, which should be stored into a secured key store or HSM
  - The authentication process should also include a public key (picked from a set of key pairs) to the claim for consumer to encrypt sensitive data in subsequent APIs consumption.  The corresponding private should also be stored in secured key store or HSM 
