# Spring Blog Project

A social media backend application built with Java and Spring Boot that provides features such as authentication, profiles, posts, comments, likes, messaging, following users, and notifications.

The project focuses on building a real-world REST API architecture using Spring Boot, JWT authentication, PostgreSQL, Spring Security, and layered backend design.

---

# Features

## Authentication

* User registration
* User login
* JWT token generation
* Secure authenticated endpoints

### Authentication Endpoints

```http
POST /register
POST /login
```

---

# Profile System

Users can create and manage personal profiles.

## Features

* Create profile
* Edit profile
* Delete profile
* Upload profile image
* View personal profile
* Search profiles
* Paginated profile retrieval

## Endpoints

```http
POST   /profile
PUT    /profile
DELETE /profile

GET    /profile/me
GET    /profile/all?page=0
GET    /profile/{userId}

GET    /profile/{userId}/image
DELETE /profile/me/image

GET    /profile/search?keyword=value&page=0
```

---

# Posts System

Users can create and interact with posts.

## Features

* Create post
* Edit post
* Delete post
* Upload post images
* Like & unlike posts
* Comment system
* Paginated posts
* Retrieve post images

## Endpoints

```http
POST   /post
PUT    /post/{postId}
DELETE /post/{postId}

GET    /post
GET    /post/{postId}

GET    /post/{postId}/image
```

---

# Likes System

## Features

* Like posts
* Unlike posts
* Retrieve post likes

## Endpoints

```http
POST   /post/{postId}/like
DELETE /post/{postId}/like

GET    /post/{postId}/like?page=0
```

---

# Comments System

## Features

* Add comments
* Edit comments
* Delete comments
* Retrieve paginated comments

## Endpoints

```http
POST   /post/{postId}/comment

PUT    /post/{postId}/comment/{commentId}

DELETE /post/{postId}/comment/{commentId}

GET    /post/{postId}/comment?page=0
```

---

# Messaging System

Users can communicate through direct messages.

## Features

* Send messages
* Retrieve chat conversations
* Edit messages
* Delete messages
* Mark messages as read

## Endpoints

```http
POST   /messages

GET    /messages/chat/{otherUserId}?page=0

POST   /messages/chat/{messageId}

PUT    /messages/chat/{messageId}

DELETE /messages/chat/{messageId}
```

---

# Follow System

Users can follow and unfollow other users.

## Features

* Follow users
* Unfollow users
* Retrieve followers
* Retrieve following
* Follow statistics

## Endpoints

```http
POST /follows/{userId}/follow
POST /follows/{userId}/unfollow

GET  /follows/{userId}/followers?page=0
GET  /follows/{userId}/following?page=0

GET  /follows/{userId}/follow-stats
```

---

# Technologies Used

* Java
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* Lombok

---

# Project Structure

```text
src/main/java/com/Omar/Spring_Blog_Project
│
├── controller
├── service
├── repository
├── dto
├── model
├── exception
├── security
├── config
└── util
```

---

# Getting Started

## Prerequisites

* Java 21+
* Maven
* PostgreSQL

---

# Clone Repository

```bash
git clone https://github.com/omaralsaleh2004/Spring_Blog_Project.git
```

Move into project directory:

```bash
cd Spring_Blog_Project
```

---

# Database Configuration

Open:

```text
src/main/resources/application.properties
```

Example configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/blogdb
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

# Run The Application

Using Maven:

```bash
mvn spring-boot:run
```

Application runs on:

```text
http://localhost:8080
```

---

# Authentication

Protected endpoints require a JWT token.

Example header:

```http
Authorization: Bearer your_token
```

---

# Learning Objectives

This project was built to improve skills in:

* Spring Boot REST APIs
* JWT Authentication
* Spring Security
* PostgreSQL Integration
* Backend Architecture
* Social Media Backend Systems
* Pagination
* File Upload Handling
* Clean Code Practices

---

# Author

Created by Omar Alsaleh
