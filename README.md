# SocialMedia2 Backend


## 🚀 Demo Videos

### 🔸 1. General Feature Walkthrough
[![Watch the demo](https://img.youtube.com/vi/rk8BPjeA3y8/0.jpg)](https://youtu.be/rk8BPjeA3y8)

🎥 [Watch the Demo Video](https://youtu.be/rk8BPjeA3y8)

Covers login, posts, likes, comments, saved posts, profile search, and more.

---

### 🔸 2. AI Feature Demo – Bot Comments 🤖
[![Watch the AI demo](https://img.youtube.com/vi/sVQQWZgtRqc/0.jpg)](https://youtu.be/sVQQWZgtRqc)

🎥 [Watch the AI demo](https://youtu.be/sVQQWZgtRqc)

Shows how AI characters (troller, good guy, optimistic bot) interact with posts using simulated personalities.


## Description
SocialMedia2 is a modern social media backend application built with Java, Spring Boot, and PostgreSQL. It provides RESTful APIs to handle user management, posts, comments, likes, and more. Designed for scalability, it integrates with Angular for the frontend and is deployed using Docker.

## Features
- **User Management**: Sign-up, login, and profile management.
- **Posts**: Create, edit, delete, and view posts.
- **Comments**: Add, edit, delete, and view comments on posts.
- **Likes**: Like and unlike posts and comments.
- **Authentication**: Basic Authentication (Will be upgraded to JWT based authentication)

## Technologies Used
- **Language**: Java 21+
- **Framework**: Spring Boot 3.x
- **Database**: PostgreSQL 15+
- **Build Tool**: Maven
- **Others**: Hibernate (JPA), Spring Security, Spring Data JPA

## Prerequisites
Ensure the following are installed on your machine:
1. Java 21 or higher
2. Maven
3. Docker
4. PostgreSQL 15 or higher

## Installation and Setup

### 1. Clone the Repository
bash
git clone https://github.com/pranaynagaraju/socialmedia2.git
cd socialmedia2

# Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/socialmedia2
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Build the Application
mvn clean install

# Run the Application
java -jar target/socialmedia2-backend-0.0.1-SNAPSHOT.jar





