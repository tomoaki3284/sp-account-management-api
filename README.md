#Leetcode backend application

## Documentation
https://tomoaki3284.atlassian.net/l/c/0br2ym2x

## Building
```shell script
mvn clean package
```

## How to run
#### Using Spring Boot Maven Plugin
```shell script
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=dev"
```

## Configuration
Change the configuration to your own in the application.properties file. Below is the path:
```shell script
/medical-center-api/src/main/resources/application.properties
```

## Request
Here is the basic request:
```shell script
curl -X POST http://localhost:8081/auth/signup
   -H 'Content-Type: application/json'
   -d '{"username":"tomo","password":"1234", "email":"tomo@yahoo.com"}'
```