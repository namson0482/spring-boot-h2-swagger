# Spring-boot-h2 Swagger

Before run application. You make sure your company already install tools below:
	
 - Maven 3.8.1 or later
 - JDK 11.0.13 or later. Source code already checked with jdk 11.0.13 and jdk 15. Both run as well. 
 - Port 8080 of computer must be available (can customize port in application.properties)
 - Spring Core, Hibernate, Swagger, Lombok, Mapstruct.
 
In this example I have used H2 as in memory database and rest web services are exposed via Swagger-UI.
Project can be executed as follows: 

 - mvn clean
 - mvn install
 - mvn spring-boot:run

After the project is up and running DB, Services and Swagger-UI can be accessed as follows:

 - DB
     - Access localhost:8080/h2-console with(JDBC URL: jdbc:h2:mem:testdb, Driver Class: org.h2.Driver, user: sa, password:)
       - Services

           - Devices
               - GET  localhost:8080/api/devices/{deviceId}?startTimestamp=&endTimestamp=
                 - deviceId required: must have value 
                 - startTimestamp and endTimestamp not required: Both can be null, format must be: yyyy-mm-dd. 
                 - it only has effective if both is not null. Either of two is null then it does not have effective
                 - Example: 
                   - if you input startTimestamp=2021-12-13 then Apps will change it to 2021-12-13 00:00:00 
                   - if you input endTimestamp=2021-12-13 then Apps will change it to 2021-12-13 23:59:59
               - POST localhost:8080/api/devices
            ````yaml
           {
              "deviceId": "xyz123",
              "latitude": 41.25,
              "longitude": -120.9762,
              "data": {
                  "humidity": 123,
                  "temperature": {
                      "unit": "C",
                      "value": "23.3"
                  }
              }
          }
          ````
		 
 - Swagger-UI: You can use the Web Browser and open the URL below to invoke Restful via Swagger-UI
     - localhost:8080/swagger-ui.html
