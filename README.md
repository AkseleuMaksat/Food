# 🍽️ Akseleu Food Service

**Smart Food Management System**

A robust and scalable Spring Boot application designed to manage food items, ingredients, and manufacturers with a secure role-based access control system. This project demonstrates a clean architecture, comprehensive testing, and modern Java development practices.

---

## 🚀 Project Overview

The **Akseleu Food Service** is a backend-focused application with a Thymeleaf frontend that allows users to browse food items, while administrators can manage the entire catalog. Use cases include:
- **User Registration & Authentication**: Secure access using Spring Security.
- **Food Catalog Management**: create, read, update, and delete food items.
- **Ingredient Tracking**: Associate ingredients with food items.
- **Manufacturer Management**: Track food sources.

The system is built to be production-ready with database migrations, automated testing, and code coverage reporting.

---

## 🛠️ Technology Stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.4.2
- **Database**: PostgreSQL
- **Migration**: Liquibase
- **Security**: Spring Security (RBAC)
- **Template Engine**: Thymeleaf
- **Testing**: JUnit 5, Mockito
- **Code Coverage**: JaCoCo
- **Build Tool**: Gradle

---

## 🏗️ Architecture

The project follows a classic layered architecture to ensure separation of concerns and maintainability:

1.  **Controller Layer**: Handles HTTP requests and manages the view (Thymeleaf templates).
2.  **Service Layer**: Contains business logic and transaction management.
3.  **Repository Layer**: Abstraction over data access using Spring Data JPA.

### Key Features
-   **RBAC (Role-Based Access Control)**:
    -   `GUEST`: View public pages.
    -   `USER`: View details.
    -   `ADMIN`: Full access to manage resources.
-   **Database Migrations**: specific SQL changesets managed via Liquibase (`src/main/resources/db/changelog`).
-   **Automated Testing**: Service layer is covered by unit tests with ~80% coverage.

---

## ✅ Functionality

-   **Auth**: Login, Registration, Logout.
-   **Dynamic Navigation**: The navigation bar adapts based on the logged-in user's role.
-   **CRUD Operations**: Full management of Foods, including assigning ingredients.
-   **Error Handling**: Graceful handling of exceptions and user feedback.

---

## 🚦 Getting Started

### Prerequisites
-   Java 21 SDK installed.
-   PostgreSQL running locally (default port 5432).
-   Gradle (or use the included wrapper).

### Installation

1.  **Clone the repository**
    ```bash
    git clone https://github.com/your-username/bitlab-food.git
    cd bitlab-food
    ```

2.  **Configure Database**
    Update `src/main/resources/application.properties` (or `application.yaml`) with your database credentials if they differ from the defaults.

3.  **Run with Gradle**
    ```bash
    ./gradlew bootRun
    ```

    The application will start at `http://localhost:8083`.

### Testing

To run the unit tests:
```bash
./gradlew test
```

To generate the code coverage report:
```bash
./gradlew test jacocoTestReport
```
The report will be available at `build/reports/jacoco/test/html/index.html`.

## 👨‍💻 Author

**Akseleu Maksat**
-   **GitHub**: [github.com/akseleumaksat](https://github.com/akseleumaksat)

---