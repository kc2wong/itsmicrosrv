# itsgateway

itsgateway is the entry point of the system.  Generally a gateway serves the following purposes :

- Simplified CORS setting
- Simplified Firewall setting
- Coarse grain end to end trust validation, e.g. validate end to end trust token
- Generate end to end trust token after validation, e.g. If upstream API consumer passes a SAML token, gateway can convert to token recognized by downstream API provider
- Implements web filter that injects commonly used value to message header, e.g. IP address of client, channel code
- Throttle rate control

Multiple gateways can be setup for different channel, e.g. one for internet channel, one for mobile and one for staff channel

itsgateway is built using spring cloud gateway.  After it starts up, it will fetch available microservices from discovery server and route the request to corresponding microservice based on uri pattern<br>
The CORS and route setting are defined in the config file itsgateway.yml.  A web filter **IpAddressWebFilter** is created to append the Ip address of incoming request to X-Forwarded-For header

