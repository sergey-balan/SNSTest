@echo:
@echo:
@echo:

rem curl -X POST http://localhost:8080/SNSNotification/rest/notify/android?message=hello
rem curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X POST --data @hello.json http://localhost:8081/SNSNotification/rest/notify/android

rem curl -i -H "Accept: text/html" -H "Content-Type: application/json" -X POST -d "{\"message\":\"hello1\"}" http://localhost:8081/SNSNotification/rest/notify/android

curl -i -H "Accept: text/html" -H "Content-Type: application/json" -X POST -d {message:hello} http://localhost:8081/SNSNotification/rest/notify/android

@echo:
@echo:
@echo:

pause