#Instruction

## git clone
Clone this repo
```shell script
git clone https://github.com/tomoaki3284/medical-center-api.git
```

## Configuration
Change the configuration to your own in the application.properties file. Below is the path:
```shell script
/medical-center-api/src/main/resources/application.properties
```

## How to run
Run the spring boot
```shell script
./mvnw spring-boot:run
```

## Request
Here is the basic request:
```shell script
curl -X POST http://localhost:8081/auth/signup
   -H 'Content-Type: application/json'
   -d '{"username":"tomo","password":"1234", "email":"tomcat@yahoo.com"}'
```