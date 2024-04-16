## Key Points
* See contract tests: src/contractTest/resources/contracts
* Spring MVC tests: telus/Apr142024/src/test/java/com/assignment/telus/todos/controller
* Service tests: telus/Apr142024/src/test/java/com/assignment/telus/todos/service
* Integration (RestTemplate test): telus/Apr142024/src/test/java/com/assignment/telus/todos/controller/ToDoControllerTestIT.java
* Liquibase changelog: telus/Apr142024/src/main/resources/changelog.xml


## Dockerization
* Dockerfile - build

```docker build --progress=plain --no-cache .``` or

```docker build --no-cache -t todos:latest .```

```docker run -d --restart=always -p 8080:8080 -p 9090:9090 todos:latest --rm```
* Run: 
* docker-compose.yml - Run the above Dockerfile + healthcheck (with actuator). 
Makes sure the service is up and running.

```docker-compose up --build --force-recreate``` or 

```docker-compose up -d```
* To stop: ```docker-compose stop|down```

## Design considerations
* GRPC works through services layer. 
Ideally we probably could avoid mapping or make it automatic
* Netty and Tomcat are not compatible. 
As long as they are in different ports it's ok, but not ideal.
* I left hibernate/JPA dependency to use @Transactional annotation.
Usually I use @Transactional for service layer. For tests, I prefer 
rollback after each test 
* I don't like MockMVC tests, I prefer Spring Cloud Contract. 
Ideally "Contract First" approach + OpenAPI. However, it is hard to redesign existing systems.
And Spring Cloud Contract tests are still reduced technically to MockMVC. Some BDDs (Cucumber, Spock)
are the same.
* Jacoco plugin is used
* Custom REST API errors: ```telus/Apr142024/src/main/java/com/assignment/telus/todos/error/CustomRestExceptionHandler.java```

