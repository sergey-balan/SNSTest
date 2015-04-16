@echo:
@echo:
@echo:

rem curl -X POST http://localhost:8080/SNSNotification/rest/notify/android/hello
rem curl -X POST http://default-environment-argzryvvar.elasticbeanstalk.com/rest/notify/android?message=hello

rem curl -H "Content-Type: application/json" -X POST --data @hello.json http://default-environment-argzryvvar.elasticbeanstalk.com/rest/notify/android

curl -i -H "Accept: text/html" -H "Content-Type: application/json" -X POST -d {message:hello} http://default-environment-argzryvvar.elasticbeanstalk.com/rest/notify/android

@echo:
@echo:
@echo:

pause