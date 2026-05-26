# Student Demo

This project demonstrates a basic layered architecture using the Spring ecosystem and follows standard backend development practices.

## Technologies Used

* Spring Boot
* Spring Data JPA
* Hibernate
* Maven

## Project Structure

The project is organized into the following layers:

### 1. Controller Layer

Handles incoming HTTP requests and returns responses to the client.

### 2. Service Layer

Contains business logic and application workflows.

### 3. Repository Layer

Manages database operations using Spring Data JPA.

### 4. Exception Handling Layer

Provides centralized exception handling for cleaner API responses and better error management.

## Features Implemented

* CRUD operations for Student entity
* Layered architecture
* Database integration using JPA and Hibernate
* Exception handling

## Student Entity

The `Student` entity contains the following fields:

| Field      | Type    |
|------------|---------|
| id         | Long    |
| fullName   | String  |
| age        | Integer |
| department | String  |

## Dependencies Used

* Spring Web
* Spring Data JPA
* Hibernate
* Database Driver (MySQL or H2)

## Learning Objectives

This project helps in understanding:

* How Spring Boot applications are structured
* JPA and Hibernate basics
* Layered backend architecture
* REST API development
* Exception handling in Spring applications
