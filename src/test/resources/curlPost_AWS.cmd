@echo:
@echo:
@echo:

rem curl -X POST http://localhost:8080/SNSNotification/rest/notify/android/hello
curl -X POST http://default-environment-argzryvvar.elasticbeanstalk.com/rest/notify/android?message=hello

@echo:
@echo:
@echo:

pause