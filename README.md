# Spring Boot Employee CRUD API

A RESTful Web Service built with Spring Boot for managing employee records. This project demonstrates standard CRUD (Create, Read, Update, Delete) operations, including both full updates (PUT) and partial updates (PATCH) using a standard layered architecture.

## Architecture Overview

The application follows standard Spring Boot design patterns, dividing responsibilities into three main layers:
* **Entity (`Employee`)**: Maps Java objects to database tables using JPA/Hibernate annotations.
* **Service (`EmployeeServiceImpl`)**: Handles business logic and manages database transactions using `@Transactional`.
* **REST Controller (`EmployeeRestController`)**: Exposes HTTP endpoints, processes incoming JSON requests, and routes them to the Service layer.

## Technologies Used

* **Java**
* **Spring Boot** (Web, Data JPA)
* **Jakarta Persistence (JPA)** for Object-Relational Mapping (ORM)
* **Jackson `JsonMapper`** for JSON payload processing (specifically for PATCH requests)

## API Endpoints

All endpoints are prefixed with `/api`.

| HTTP Method | Endpoint | Description | Request Body |
|---|---|---|---|
| **GET** | `/employees` | Retrieves a list of all employees. | None |
| **GET** | `/employees/{id}` | Retrieves a specific employee by ID. | None |
| **POST** | `/employees` | Creates a new employee. | JSON (Employee object) |
| **PUT** | `/employees` | Fully updates an existing employee. | JSON (Employee object) |
| **PATCH** | `/employees/{id}` | Partially updates an employee. | JSON (Field mappings) |
| **DELETE**| `/employees/{id}` | Deletes an employee by ID. | None |

## Request Examples

### POST - Add a New Employee
Endpoint: `POST /api/employees`

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@luv2code.com"
}

```

### PATCH - Partially Update an Employee

Endpoint: `PATCH /api/employees/1`
*(Note: Passing an `"id"` in the payload will trigger an exception, as ID modification is not allowed).*

```json
{
  "email": "new.email@luv2code.com"
}

```

## Error Handling

The API includes basic exception handling in the controller logic. If a client attempts to retrieve, patch, or delete an employee ID that does not exist, the API will throw a `RuntimeException` detailing that the ID was not found.

```
