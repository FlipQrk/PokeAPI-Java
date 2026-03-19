# Pokémon API

<div align="center">
  
**A Spring Boot REST API wrapper for the public PokéAPI with reactive non-blocking operations**

[![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.10-green?style=flat-square&logo=spring)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9+-blue?style=flat-square&logo=apache-maven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)](LICENSE)

</div>

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Architecture & Design](#architecture--design)
- [Error Handling](#error-handling)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

This is a **Spring Boot REST API** that acts as a wrapper around the public [PokéAPI](https://pokeapi.co/), providing simple, efficient endpoints to fetch Pokémon data. It leverages **Spring WebFlux** for asynchronous, non-blocking HTTP operations, ensuring optimal performance and low latency.

### Why This Project?

- 🚀 **Reactive Architecture**: Built with Spring WebFlux for non-blocking operations
- 🎯 **Simplified Endpoints**: Easy-to-use REST endpoints without complex filtering
- ⚡ **Minimal Response Payload**: Returns only the data you need (DTOs)
- 🛡️ **Error Handling**: Comprehensive exception handling with custom error responses
- 📊 **Logging**: Built-in logging for debugging and monitoring

---

## Features

✅ **Get Pokémon Details**
- Retrieve basic information (name, height, weight, abilities) for any Pokémon

✅ **Get Pokémon Images**
- Fetch all available images for a specific Pokémon

✅ **Filter by Generation**
- Get all Pokémon from a specific generation with optional type filtering

✅ **Filter by Type**
- Retrieve all Pokémon of a specific type (fire, water, grass, etc.)

✅ **Robust Error Handling**
- Custom exceptions for missing Pokémon
- Standardized error response format
- Detailed logging for troubleshooting

---

## Prerequisites

Before you begin, ensure you have the following installed on your system:

| Requirement | Version | Download |
|-------------|---------|----------|
| **Java** | 21+ | [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.java.net/) |
| **Maven** | 3.6+ | [Maven Central](https://maven.apache.org/download.cgi) |
| **Git** | Latest | [Git SCM](https://git-scm.com/) |

### Verify Installation

```bash
java -version
mvn -version
git --version
```

---

## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/FlipQrk/PokeAPI-Java.git
cd pokemonapi
```

### 2. Install Dependencies

```bash
mvn clean install
```

This command will:
- Download all required dependencies from Maven Central
- Compile the source code
- Run all tests (if configured)
- Create the build artifacts

### 3. Verify the Build

```bash
mvn verify
```

---

## Running the Application

### Option 1: Using Maven (Recommended during Development)

```bash
mvn spring-boot:run
```

The application will start on the default port: **http://localhost:8080**

### Option 2: Using the JAR file (Production)

First, build the JAR:

```bash
mvn clean package
```

Then run it:

```bash
java -jar target/pokemonapi-0.0.1-SNAPSHOT.jar
```

### Option 3: Using IDE

If you're using an IDE like IntelliJ IDEA or Eclipse:

1. Open the project in your IDE
2. Right-click on `PokemonapiApplication.java`
3. Select **Run** or **Debug**

### Verify the Application is Running

```bash
curl http://localhost:8080/pokemon/pikachu
```

You should receive a JSON response with Pikachu's data.

---

## API Endpoints

All endpoints are prefixed with `/pokemon`

### 1. Get Pokémon Details

**Request:**
```
GET /pokemon/{name}
```

**Parameters:**
- `name` (required, path): Pokémon name (case-insensitive)

**Example:**
```bash
curl http://localhost:8080/pokemon/pikachu
```

**Response (200 OK):**
```json
{
  "name": "pikachu",
  "height": 4,
  "weight": 60,
  "abilities": ["static", "lightning-rod"]
}
```

**Error Response (404 Not Found):**
```json
{
  "status": 404,
  "message": "Pokemon 'xyz' not found",
  "timestamp": "2025-03-19T10:30:00Z"
}
```

---

### 2. Get Pokémon Images

**Request:**
```
GET /pokemon/{name}/images
```

**Parameters:**
- `name` (required, path): Pokémon name

**Example:**
```bash
curl http://localhost:8080/pokemon/pikachu/images
```

**Response (200 OK):**
```json
{
  "name": "pikachu",
  "imageUrls": [
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/pokemon/...",
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/pokemon/..."
  ]
}
```

---

### 3. Get Pokémon by Generation

**Request:**
```
GET /pokemon/gen/{gen}?tipo={type}
```

**Parameters:**
- `gen` (required, path): Generation number (1-9)
- `tipo` (optional, query): Type filter (fire, water, grass, etc.)

**Examples:**
```bash
# Get all Pokémon from generation 1
curl http://localhost:8080/pokemon/gen/1

# Get all Fire-type Pokémon from generation 1
curl http://localhost:8080/pokemon/gen/1?tipo=fire
```

**Response (200 OK):**
```json
{
  "generation": 1,
  "type": "fire",
  "pokemon": [
    {
      "name": "charmander",
      "height": 6,
      "weight": 85,
      "abilities": ["blaze"]
    },
    {
      "name": "charmeleon",
      "height": 11,
      "weight": 190,
      "abilities": ["blaze"]
    }
  ]
}
```

---

### 4. Get Pokémon by Type

**Request:**
```
GET /pokemon?tipo={type}
```

**Parameters:**
- `tipo` (required, query): Pokémon type (fire, water, grass, electric, etc.)

**Example:**
```bash
curl http://localhost:8080/pokemon?tipo=water
```

**Response (200 OK):**
```json
{
  "type": "water",
  "pokemon": [
    {
      "name": "squirtle",
      "height": 5,
      "weight": 90,
      "abilities": ["torrent"]
    },
    {
      "name": "wartortle",
      "height": 10,
      "weight": 225,
      "abilities": ["torrent"]
    }
  ]
}
```

---

## Project Structure

```
pokemonapi/
├── src/main/java/com/esteban/pokemonapi/
│   ├── PokemonapiApplication.java          # Spring Boot entry point
│   ├── Config/
│   │   ├── RestTemplateConfig.java         # REST template configuration
│   │   └── WebClientConfig.java            # WebClient (WebFlux) configuration
│   ├── Controller/
│   │   └── HelloController.java            # REST endpoints (@GetMapping, etc.)
│   ├── Service/
│   │   └── PokemonService.java             # Business logic and API calls
│   ├── DTO/                                # Data Transfer Objects
│   │   ├── PokemonDTO.java                 # Pokémon basic info DTO
│   │   ├── PokemonImageDTO.java            # Pokémon images DTO
│   │   ├── PokemonTypeDTO.java             # Pokémon by type DTO
│   │   └── PokemonGenDTO.java              # Pokémon by generation DTO
│   ├── Model/                              # Response models from PokéAPI
│   │   ├── PokemonResponse.java
│   │   ├── PokemonImageResponse.java
│   │   ├── PokemonTypeResponse.java
│   │   └── PokemonGenResponse.java
│   └── Exception/
│       ├── PokemonNotFoundException.java    # Custom exception
│       ├── ErrorResponse.java              # Error response format
│       └── GlobalExceptionHandler.java     # Centralized exception handler
├── src/main/resources/
│   └── application.properties              # Application configuration
├── src/test/java/                          # Unit tests
├── pom.xml                                 # Maven configuration
└── README.md                               # This file
```

### Key Directories Explained

| Directory | Purpose |
|-----------|---------|
| **Config/** | Bean configurations (RestTemplate, WebClient setup) |
| **Controller/** | HTTP request handlers and route definitions |
| **Service/** | Business logic and external API communication |
| **DTO/** | Simplified data objects for API responses |
| **Model/** | Response mapping models from PokéAPI |
| **Exception/** | Custom exceptions and error handling |

---

## Technologies Used

### Core Framework
- **Spring Boot 3.5.10** - Enterprise Java framework
- **Spring WebFlux** - Reactive, non-blocking HTTP client
- **Spring Web** - REST controller and MVC support

### Build & Dependency Management
- **Maven 3.6+** - Build automation and dependency management
- **Java 21** - Latest long-term support Java version

### Reactive Ecosystem
- **Reactor** - Reactive streams implementation (Mono, Flux)
- **WebClient** - Non-blocking REST client

### Other Libraries
- **SLF4J** - Logging facade
- **Logback** - Logging implementation
- **JUnit 5** - Unit testing framework

---

## Architecture & Design

### Design Patterns Used

#### 1. **Dependency Injection**
```java
public HelloController(PokemonService pokemonService) {
    this.pokemonService = pokemonService;
}
```
Dependencies are injected via constructor, improving testability and loose coupling.

#### 2. **Data Transfer Objects (DTOs)**
Separate DTOs are used to expose only necessary data to clients, hiding internal API details:

```
PokéAPI Response → Model (internal) → DTO (exposed) → Client
```

#### 3. **Service Layer**
Business logic is isolated in the `PokemonService` class, separating concerns from HTTP handling.

#### 4. **Global Exception Handling**
`GlobalExceptionHandler` provides centralized exception handling for consistent error responses.

#### 5. **Reactive Non-Blocking Architecture**
Uses `WebFlux` and `Mono` to handle I/O operations asynchronously:

```java
webClient.get()
    .uri("/pokemon/" + name)
    .retrieve()
    .bodyToMono(PokemonResponse.class)
    .map(response -> mapToDTO(response))
    .block(); // Temporary blocking for current architecture
```

---

## Error Handling

### Custom Exception: `PokemonNotFoundException`

When a Pokémon is not found, the application throws a `PokemonNotFoundException`:

```java
.onStatus(status -> status.is4xxClientError(), response -> {
    logger.error("Pokemon no encontrado: {}", name);
    return Mono.error(new PokemonNotFoundException(name));
})
```

### Global Exception Handler

`GlobalExceptionHandler` catches all exceptions and returns a standardized error response:

```json
{
  "status": 404,
  "message": "Pokemon 'xyz' not found",
  "timestamp": "2025-03-19T10:30:00Z"
}
```

### Common Error Codes

| Status | Meaning | Example |
|--------|---------|---------|
| 200 | Success | Pokémon found and returned |
| 400 | Bad Request | Invalid input parameters |
| 404 | Not Found | Pokémon doesn't exist |
| 500 | Server Error | Internal application error |

---

## Development

### Building from Source

```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Create package
mvn package

# Skip tests during build
mvn package -DskipTests
```

### Running Tests

```bash
mvn test
```

Tests are located in `src/test/java/` and use JUnit 5.

### Debugging

1. Set a breakpoint in your IDE
2. Run the application in debug mode:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.arguments="--debug"
   ```
3. Connect your IDE debugger to port 5005

### Logging

The application uses SLF4J with Logback. Configure logging in `application.properties`:

```properties
logging.level.com.esteban.pokemonapi=INFO
logging.level.org.springframework.web=DEBUG
```

---

## Future Enhancements

- [ ] Caching layer (Redis) for frequently accessed data
- [ ] Pagination for large result sets
- [ ] API rate limiting and throttling
- [ ] Database integration for user-specific data
- [ ] Full async operations (remove `.block()` calls)
- [ ] GraphQL endpoint as alternative to REST
- [ ] OpenAPI/Swagger documentation
- [ ] Authentication and authorization (JWT)
- [ ] Docker containerization
- [ ] Kubernetes deployment manifests

---

## Contributing

Contributions are welcome! To contribute:

1. **Fork** the repository
2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. **Make your changes** and commit them with clear messages
   ```bash
   git commit -m "Add feature: description"
   ```
4. **Push to your fork**
   ```bash
   git push origin feature/your-feature-name
   ```
5. **Open a Pull Request** with a description of your changes

### Code Style Guidelines

- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Use meaningful variable names
- Add comments for complex logic
- Write unit tests for new features
- Keep methods focused and single-responsibility

---

## Troubleshooting

### Issue: Application fails to start

**Solution:**
- Ensure Java 21 is installed: `java -version`
- Check that Maven can download dependencies: `mvn dependency:resolve`
- Review logs for specific errors

### Issue: Cannot connect to PokéAPI

**Solution:**
- Verify internet connection
- Check if https://pokeapi.co/api/v2/ is accessible
- Review network/firewall settings

### Issue: Pokémon not found error

**Solution:**
- Verify Pokémon name spelling (case-insensitive)
- Check PokéAPI documentation for correct naming conventions
- Some Pokémon names use hyphens (e.g., "mr-mime")


---

## Support & Contact

- 📧 **Email**: [raigozacarmonaesteban@gmail.com]
- 💬 **Issues**: [GitHub Issues](https://github.com/FlipQrk/pokemonapi/issues)
- 📖 **Documentation**: See [PokéAPI Docs](https://pokeapi.co/docs/v2)

---

<div align="center">

Made with ❤️ by Esteban

⭐ If you find this project useful, please consider giving it a star!

</div>
