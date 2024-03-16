# Library Management Microservices

A collection of REST API services for managing Users, Books, and Libraries using Java and Spring Boot. The microservices are `user-service`, `book-service`, `library-service`, `eureka-server`, and `gateway`.

## Architecture

This is a Microservice-based application, a client's request hits the `gateway`, which routes it to the `library-service`. The `library-service` internally uses `user-service` and `book-service` to implement complex business requirements. All the services register themselves on the 'eureka-server' which acts as a Service Registry and facilitates Service Discovery.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
- A text editor or an IDE (like IntelliJ)
- JDK version 8 or later
- Maven for dependency management
- MySQL Server for the database
- Git

### Installing & Running
1. Clone the project from the GitHub repository
    ```
    git clone [repositoryUrl]
    ```
2. Navigate into the root of the project directory `library-management`
3. Start the individual services by navigating into their directories (`user-service`, `book-service`, `library-service`, `eureka-server`, `gateway`) and issuing below command for each.
    ```
    ./mvnw spring-boot:run
    ```

Your Library Management API is now up and running, and ready to receive requests on http://localhost:8080.

## Testing
The APIs in this project provide a suite of unit tests for testing all features, which are located in the individual projects. To run the tests, navigate to the respective project directory and issue:
    ```
    ./mvnw test
    ```
    
## License
This project is licensed under the MIT License - see the LICENSE.md file for details
