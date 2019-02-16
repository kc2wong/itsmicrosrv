# itsmonolith

Sometimes, the same application will be deployed to multiple clients.  Some clients may expect lower usage rate and therefore a full scale mircoservice architecture can be too complex and a monolithic application is more appropriate in this use case.

In development environment, it is also more convenient to start a monolithic application for consumer applications (e.g. web and mobile) to perform integration
 
Create a monolith application is also simple :
1. Add dependency to all required microservices (client and server) in build.gradle
2. Create a configuration class to instruct spring to scan and create required server side components and beans [See ApplicationConfig.kt]
3. Create a configuration class to setup CORS [See CorsConfig.kt, setting should be same as itsgateway]
4. Create a configuration class to setup spring security [See SecurityConfig.kt, setting should be same as itsgateway]
5. Create a bootstrap.yml file to disable sping cloud feature (discovery and config client) [See resources/bootstrap.yml]
6. Create a application.yml file which combines the setting of all yml files in application-config [See resources/application.yml]

Start itsmonolith using the following steps

```
$ cd itsmicrosrv\
$ gradlew itsmonolith:bootRun
```

If high availability is required, it can still archived by setting up a Gateway + Discovery + multiple instances of monolithic application 
