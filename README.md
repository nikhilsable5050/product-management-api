# Product Management API

A RESTful Product Management API built using Spring Boot, Spring Security, JWT authentication, PostgreSQL, Docker, Docker Compose, and Swagger/OpenAPI.

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT Authentication
- PostgreSQL
- Maven
- Docker
- Docker Compose
- Swagger / OpenAPI
- Lombok

---

## Features

### Authentication

- User registration
- User login
- JWT access token
- Refresh token
- Refresh token rotation
- Role-based authorization

### Product Management

- Create product
- Get all products with pagination
- Get product by ID
- Update product
- Delete product
- Request validation
- Global exception handling

### Item Management

- Create item for a product
- Get items by product
- Product-item relationship

### API Documentation

- Swagger UI
- OpenAPI documentation

---

## Architecture

The application follows a layered architecture.

```text
Client
  |
  v
Controller Layer
  |
  v
Service Layer
  |
  v
Repository Layer
  |
  v
Entity Layer
  |
  v
PostgreSQL Database
Controller Layer

Handles HTTP requests and responses.

Examples:

Authentication requests
Product requests
Item requests
Service Layer

Contains the application's business logic.

The service layer:

Processes requests
Performs validations/business rules
Communicates with repositories
Converts entities to DTOs
Repository Layer

Uses Spring Data JPA and JpaRepository to communicate with PostgreSQL.

Entity Layer

Contains JPA entities representing database tables.

Examples:

User
Product
Item
RefreshToken
DTO Layer

DTOs are used to control the data sent to and received from the API.

Examples:

ProductRequest
ProductResponse
ItemRequest
ItemResponse

This prevents database entities from being directly exposed through the API.

Security Layer

Handles:

JWT authentication
JWT authorization
Refresh tokens
Refresh token rotation
Role-based access control
Authentication Flow
User
 |
 | Register
 v
Register API
 |
 v
User stored in PostgreSQL
 |
 | Login
 v
Login API
 |
 v
Access Token + Refresh Token
 |
 v
Access protected APIs
 |
 | Access Token expires
 v
Refresh Token API
 |
 v
New Access Token + New Refresh Token
Authorization

The application supports the following roles:

USER
ADMIN
Product APIs

Authenticated users with appropriate roles can access product APIs.

Admin APIs

Admin APIs require the ADMIN role.

API Base URL
http://localhost:8080/api/v1
Swagger UI

After starting the application, Swagger UI is available at:

http://localhost:8080/swagger-ui/index.html

Swagger can be used to test the APIs directly from the browser.

Running the Application
Prerequisites

Install the following:

Java 21
Maven
Docker Desktop
Git
Run Using Docker Compose

The recommended way to run the complete application is using Docker Compose.

From the project root directory:

docker compose up

Docker Compose starts:

Spring Boot Application
        |
        v
PostgreSQL Database

The application will be available at:

http://localhost:8080

Swagger UI:

http://localhost:8080/swagger-ui/index.html

PostgreSQL:

localhost:5432
Stop Docker Containers
docker compose down

The PostgreSQL database uses a Docker volume, so database data persists when the containers are stopped.

To remove the containers and database volume:

docker compose down -v

Warning: docker compose down -v removes the PostgreSQL Docker volume and its stored data.

Docker Architecture

The project uses two Docker services:

                    Docker Compose
                         |
             +-----------+-----------+
             |                       |
             v                       v
       Spring Boot              PostgreSQL
       Application              Database
       Port 8080                Port 5432
             |                       |
             +---- postgres:5432 ----+

The Spring Boot application connects to PostgreSQL using the Docker Compose service name:

jdbc:postgresql://postgres:5432/product_management

The application does not use localhost to communicate with PostgreSQL inside Docker.

Dockerfile

The project uses a multi-stage Docker build.

Maven Build Stage
       |
       v
Build Spring Boot JAR
       |
       v
Java 21 Runtime Image
       |
       v
Run application

The multi-stage build keeps the runtime image separate from the Maven build environment.

Database

The application uses PostgreSQL.

Docker Compose creates:

Database: product_management
Username: postgres
Port: 5432

The PostgreSQL data is stored using a Docker volume:

postgres_data
Validation

The application uses Jakarta Bean Validation for request validation.

Examples:

Product
Product name is required
Product name cannot be blank
Product name cannot exceed 255 characters
Item
Quantity is required
Quantity must be at least 1
Exception Handling

The application uses a global exception handler with @RestControllerAdvice.

Handled errors include:

Product not found
Invalid request data

The API returns structured error responses containing:

status
message
timestamp

Project Structure
product-management-api/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── zestindia/
│       │           └── productmanagement/
│       │               │
│       │               ├── config/
│       │               │
│       │               ├── controller/
│       │               │
│       │               ├── dto/
│       │               │
│       │               ├── entity/
│       │               │
│       │               ├── exception/
│       │               │
│       │               ├── repository/
│       │               │
│       │               ├── security/
│       │               │
│       │               └── service/
│       │
│       └── resources/
│           └── application.properties
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
Running Without Docker

If you want to run the application directly using Maven, make sure PostgreSQL is running locally and the database is available.

Then run:

Windows
.\mvnw.cmd spring-boot:run
Linux / macOS
./mvnw spring-boot:run

The application will start on:

http://localhost:8080
Build the Docker Image Manually

To build the Docker image:

docker build -t product-management-api .

Check the created image:

docker images
API Testing

The APIs can be tested using Swagger UI:

http://localhost:8080/swagger-ui/index.html

Authentication should be performed first to obtain an access token.

The access token can then be used to access protected endpoints.

Main API Endpoints
Authentication
POST /api/v1/auth/register
POST /api/v1/auth/login
POST /api/v1/auth/refresh
Products
POST   /api/v1/products
GET    /api/v1/products
GET    /api/v1/products/{id}
PUT    /api/v1/products/{id}
DELETE /api/v1/products/{id}
Items
POST /api/v1/products/{productId}/items
GET  /api/v1/products/{productId}/items


Author
Nikhil Sable
