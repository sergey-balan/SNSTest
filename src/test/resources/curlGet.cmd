rem curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET GET http://localhost:8080/SNSNotification/rest/hello
@echo off
@echo:
@echo:
@echo:
curl -X GET http://localhost:8080/SNSNotification/rest/hello &
@echo:
@echo:
@echo:
pause