# itsconfig

itsconfig is the centralized configuration server in system.  It reads the yml files from github repo [itsmicrosrv/application-config](https://github.com/kc2wong/itsmicrosrv/tree/master/application-config)<br>
To change the location of the repo, modify the file itsmicrosrv\serv-config\src\main\resources\bootstrap.yml<br>
When a microservice request to get configuration from itsconfig, it will return application.yml and [mciroservice-name].yml, e.g. itsorder.yml



