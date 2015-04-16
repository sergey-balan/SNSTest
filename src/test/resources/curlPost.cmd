@echo:
@echo:
@echo:

rem curl -X POST http://localhost:8080/SNSNotification/rest/notify/android/hello
rem curl -X POST http://localhost:8080/SNSNotification/rest/notify/android?message=hello

curl -X POST http://localhost:8080/SNSNotification/rest/notify/android -d "message":"hello"

@echo:
@echo:
@echo:

pause