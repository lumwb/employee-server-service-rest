# Employee Management

A simple web application with 2 HTTP endpoints for Employee management.

## Description
As part of a SWE challenge, there were 2 endpoints to be created. A get method with various parameters and standard functionality. And a post method which updates the database from a CSV file


## Getting Started

### Configuration
1. Start mysql database and set connection string details in /resources/application.properties
2. Set required pre-loaded data in /resources/import.sql

### Executing program

* To run the server directly:
```
./mvnw spring-boot:run
```

### API Documentation
Swagger has been included in the codebase and can be accessed at localhost:8080/swagger-ui.html 

However would suggest using postman / CURL to test the API's directly.

### Test Files
* Good CSV file: /testFiles/goodInput.csv
* Bad CSV file: /testFiles/badInput.csv

## Design Considerations

### DTO or nah?
Personally, Data Transfer Objects should almost always be included to decouple the model layer from the UI / Controller layer. But given the basic requirements of the project, DAO's will used "directly" without mapping each DAO to a DTO. However generics were used in `GetUsersResponse` in an attempt to decouple the layers.

### JPARespostiory + Dyanmic Query (Specification) VS Custom Repository (EM)
The spring framework provides a very nice `JPARepository` which provides the basic CRUD functionality for each DAO. And also allowes for the generation dynamic queries by extending the `JPASpecificationRepository`. This covers most of the requirements for optional arguments, however the API does not provide direct support for offset and limit without a [workaround with pagination](https://blog.felix-seifert.com/limit-and-offset-spring-data-jpa-repositories/)

I chose to create my own custom get method directly calling the `EntityManager` in `EmployeeCustomRepositoryCustom` as method `findEmployeeCustom`. This allows the emplyoee repository to still inherit the methods from `JPARepository` while adding my own functionality. Specifically, the methods we are interested in using are `.setFirstResult(offset)` and `.setMaxResult(limit)`.

Personally, this was a better method than using the `@Query` as native SQL queries. Which "breaks" abstraction and constructs the SQL query directly. Additionally, that implementation wouldn't have the dynamic nature we require. (e.g. parameters are optional and require different query)

### Id vs Name as pKey
I chose to use the employee (user) name as the primary key for the employee table instead of creating an auto-generated Id. Possibly a "shortcut" as we can make use of the `.saveAll()` method from the `JPARepository` to automatically "create or update" each entry in the database for the `/upload` endpoint. 

If the project requires further functionality, such as updating an employee's name, then having an employeeId would make more sense. And each entry in the CSV file would have to be retrieved / check if exists in database before deciding on update / creation.

## Future Work / Scalability
In general, to improve scalability we can include multiple-processes on the server side to handle multiple concurrent requests. A furhter measure of having multiple (dsitributed) servers with a single database coupled with a load balancer can also be explored. Since the database (SQL-based) supports consistency, there shouldn't be an issue handling multiple writes to it concurrently.

## Authors

[@lumweiboon](http://lumwb.github.io)


