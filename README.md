# Smart Contacts Management System
> [!IMPORTANT]  
> The project was up and running and live at: https://smartcontacts.rajpalival.me/home.
> Since the new user has to verify his email unless he/she is signing in using google or GitHub,
> So I have created a dummy user for testing purposes.
> Email: admin@gmail.com
> Password: admin

> [!IMPORTANT]  
> But unfortunately AWS started billing me with $50 and I can't really afford it for now hence
> I will be finding alternate ways to host it for free.
## Project Overview

Smart Contacts Management System is a web-based application that allows users to manage their contacts effectively. The application provides features like creating, reading, updating, and deleting contacts. It also supports OAuth2 authentication with Google and GitHub, alongside custom login logic. The application is built using Java, Spring Boot, Spring MVC pattern, Thymeleaf, Vanilla JavaScript and Flow bite UI.

## Key Features

* **Contact Management**: Users can create, read, update, and delete contacts from the application.
* **Pagination**: The application supports pagination for the contacts list, allowing users to navigate through the contacts list with ease.
* **Searching**: Users can search for contacts based on various criteria like name, email, phone number, etc.
* **OAuth2 Authentication**: The application supports OAuth2 authentication with Google and GitHub.
* **Custom Login Logic**: The application supports custom login logic for users who prefer to log in with their email and password.

## Technologies Used

* **Java**: The application is built using Java 8.
* **Spring Boot**: The application is built using Spring Boot 2.7.
* **Thymeleaf**: Thymeleaf is used for rendering HTML templates.
* **Bootstrap**: Bootstrap is used for styling the application.
* **OAuth2**: The application supports OAuth2 authentication with Google and GitHub.
* **Spring Security**: The application uses Spring Security for authentication and authorization.

## Getting Started

## Prerequisites

* Java 21
* Maven

## Installation

1. Clone the repository.
2. Open a terminal/command prompt and navigate to the project's root directory.
3. Run `mvn clean install` to build the project.
4. Run `java -jar target/smart-contacts-0.0.1-SNAPSHOT` to start the application.

### Usage

1. Open your web browser and navigate to `http://localhost:8081` to access the application.
2. Sign up for an account using the custom login logic or use the OAuth2 authentication with Google and GitHub.
3. After successful login, you can create, read, update, and delete contacts from the application.

### Contact

For more information, please contact the project maintainer at `rajpalival21@gmail.com`.

### Acknowledgments

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Thymeleaf](https://www.thymeleaf.org/)
* [Bootstrap](https://getbootstrap.com/)
* [OAuth2](https://oauth.net/2/)
