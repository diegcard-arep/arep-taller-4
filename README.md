# WebApp Framework – Minimalist HTTP Server in Java

<div align="center">
  <img src="src/main/resources/static/logo.svg" alt="WebApp Framework Logo" width="200"/>
  
  [![Java](https://img.shields.io/badge/Java-21+-orange.svg)](https://www.oracle.com/java/)
  [![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
  [![Docker](https://img.shields.io/badge/Docker-24+-blue.svg)](https://www.docker.com/)
  [![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE.md)
</div>

## 📋 Overview

A lightweight, sequential HTTP server built from scratch in Java 21, featuring a custom IoC (Inversion of Control) microframework. This project demonstrates enterprise architecture principles by implementing a web server capable of serving static content and dynamic endpoints through annotation-based controllers.

### 🎯 Academic Purpose

**Enterprise Architecture Exercise (AREP) – Workshop 3**  
*Escuela Colombiana de Ingeniería Julio Garavito*

This project explores fundamental concepts of web server implementation, reflection, dependency injection, and dynamic routing in Java, serving as a foundation for understanding distributed architectures and microservices.

## ✨ Key Features

### 🌐 Core Server Capabilities
- **Custom HTTP Server**: Built from scratch without external web frameworks
- **Static Content Delivery**: Serves HTML, CSS, JavaScript, PNG, SVG, and other static assets
- **MIME Type Detection**: Automatic content-type resolution based on file extensions
- **Comprehensive Error Handling**: Custom 400, 404, and 500 error responses
- **CORS Support**: Cross-Origin Resource Sharing for GET/POST/OPTIONS requests
- **Multi-format Support**: Handles various file types with proper MIME detection

### ⚡ IoC Microframework
- **Annotation-Driven Architecture**: Uses `@RestController`, `@GetMapping`, and `@RequestParam`
- **Automatic Controller Discovery**: Scans classpath for annotated controllers using reflection
- **Dependency Injection**: Parameter injection through custom annotations
- **Dynamic Routing**: Routes are registered automatically at startup
- **Zero Configuration**: No XML files or manual configuration required

## 🏗️ Architecture

### System Overview
<div align="center">
  <img src="img/arquitecture.png" alt="System Architecture Diagram" width="800"/>
  <p><em>System Architecture - Component Interaction</em></p>
</div>

### Request Flow
<div align="center">
  <img src="img/diagram_flow.png" alt="Request Flow Diagram" width="800"/>
  <p><em>Request Processing Flow</em></p>
</div>

The server follows a streamlined request-response cycle:

1. **Request Reception**: Client sends HTTP request to the server
2. **Request Parsing**: Server parses HTTP headers and extracts routing information
3. **Route Resolution**: Static files are served directly or dynamic endpoints are invoked
4. **Response Generation**: Appropriate response is formatted and sent back to client
5. **Connection Management**: Connection is properly closed after response

## 💡 Framework Usage

### Creating Controllers

```java
import com.escuelaing.arep.annotations.GetMapping;
import com.escuelaing.arep.annotations.RequestParam;
import com.escuelaing.arep.annotations.RestController;

@RestController
public class DemoController {
    
    /**
     * Simple greeting endpoint with optional parameter
     */
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello " + name + "!";
    }
    
    /**
     * Health check endpoint
     */
    @GetMapping("/api/status")
    public String getStatus() {
        return "{\"status\":\"Server is running\",\"timestamp\":\"" + 
               java.time.Instant.now() + "\"}";
    }
    
    /**
     * Dynamic content example
     */
    @GetMapping("/api/info")
    public String getServerInfo() {
        return "{\"framework\":\"WebApp Custom\",\"version\":\"1.0\",\"java\":\"" + 
               System.getProperty("java.version") + "\"}";
    }
}
```

### Automatic Controller Discovery

The framework leverages Java reflection to:
- **Classpath Scanning**: Automatically discovers classes annotated with `@RestController`
- **Method Registration**: Identifies and registers methods annotated with `@GetMapping`
- **Parameter Injection**: Configures parameter binding using `@RequestParam` annotations
- **Route Table Building**: Constructs the routing table dynamically at server startup

> **No manual configuration required** – simply add annotations and the framework handles discovery, registration, and routing automatically.

## 🚀 Getting Started

### Prerequisites

| Requirement | Version | Purpose |
|-------------|---------|---------|
| **Java** | 21+ | Runtime environment |
| **Maven** | 3.6+ | Build and dependency management |
| **Docker** | 24+ | Containerization (optional) |
| **Git** | Latest | Source code management |

### 🔧 Installation & Execution

#### Method 1: Maven Direct Execution (Recommended for Development)

```bash
# Clone the repository
git clone https://github.com/diegcard-arep/arep-taller-4.git
cd arep-taller-4

# Compile the project and dependencies
mvn clean compile

# Run the server
java -cp target/classes com.escuelaing.arep.HttpServer
```

#### Method 2: Standalone JAR (Recommended for Production)

```bash
# Build executable JAR package
mvn clean package

# Run from JAR (includes all dependencies)
java -jar target/urlobject-1.0-SNAPSHOT.jar
```

#### Method 3: Docker Deployment (Recommended for Cloud)

```bash
# Using Docker Compose (Includes environment setup)
docker-compose up --build

# Or build and run manually
docker build -t arep-taller-4 .
docker run -p 35000:35000 --name arep-webapp diegcard/arep-taller-4:latest
```

### 🌐 Accessing the Application

Once started, access the application at:
```
🌐 http://localhost:35000
```

#### Quick Health Check
```bash
# Test server availability
curl http://localhost:35000/hello

# Expected response: "Hello World!"
```

> **💡 Pro Tip**: Static files are served from `target/classes/static`. Always compile the project before running to ensure resources are available in the classpath.

## 🐳 Cloud Deployment

### AWS EC2 Deployment Guide

This application can be deployed on any Docker-compatible cloud platform. Here's a step-by-step guide for AWS EC2:

#### Step 1: EC2 Instance Setup
<div align="center">
  <img src="img/image-ec2.png" alt="EC2 Instance Images" width="800"/>
  <p><em>EC2 Instance Dashboard</em></p>
</div>

#### Step 2: SSH Connection
<div align="center">
  <img src="img/ec2-session.png" alt="EC2 SSH Session" width="800"/>
  <p><em>SSH Connection to EC2 Instance</em></p>
</div>

#### Step 3: Docker Installation

```bash
# Update system packages
sudo yum update -y

# Install Docker
sudo yum install docker -y

# Start Docker service
sudo service docker start

# Add user to docker group
sudo usermod -a -G docker ec2-user

# Verify installation
docker --version
```

#### Step 4: Security Group Configuration
<div align="center">
  <img src="img/ec2-security-groups.png" alt="EC2 Security Groups Configuration" width="800"/>
  <p><em>Security Groups - Port 35000 Configuration</em></p>
</div>

Ensure the following inbound rules are configured:
- **Type**: Custom TCP
- **Port Range**: 35000
- **Source**: 0.0.0.0/0 (or your specific IP range)

#### Step 5: Container Deployment
<div align="center">
  <img src="img/ec2-docker.png" alt="Docker Container Running on EC2" width="800"/>
  <p><em>Docker Container Successfully Running</em></p>
</div>

```bash
# Pull and run the container
docker run -p 35000:35000 --name arep-webapp diegcard/arep-taller-4:latest
```

#### Step 6: Application Verification
<div align="center">
  <img src="img/ec2-app.png" alt="Application Running on EC2" width="800"/>
  <p><em>WebApp Framework Running on AWS EC2</em></p>
</div>

#### Step 7: API Testing
<div align="center">
  <img src="img/ec2-postman.png" alt="Postman API Testing on EC2" width="800"/>
  <p><em>API Endpoint Testing with Postman</em></p>
</div>

### 📺 Demonstration Video

**Complete functionality walkthrough**: [https://youtu.be/TjGrRkTWKBM](https://youtu.be/TjGrRkTWKBM)

## ⚙️ Advanced Configuration

### Server Port Configuration

```java
import com.escuelaing.arep.config.ServerConfig;

// Change default port (35000)
public class CustomServerConfig {
    public static void main(String[] args) {
        ServerConfig.setPort(8080);
        // Start server with custom port
    }
}
```

### Static Files Directory Configuration

```java
import com.escuelaing.arep.HttpServer;

public class StaticFileConfig {
    public static void setupStaticFiles() {
        // Default static directory
        HttpServer.setStaticFilesDirectory("/static");
        
        // Custom path for development
        HttpServer.setStaticFilesDirectory("src/main/resources/static");
        
        // Production path
        HttpServer.setStaticFilesDirectory("/var/www/html");
    }
}
```

### Supported MIME Types

| File Extension | MIME Type | Use Case |
|----------------|-----------|----------|
| html, htm | text/html | Web pages |
| css | text/css | Stylesheets |
| js | application/javascript | Client-side scripts |
| png | image/png | Images |
| jpg, jpeg | image/jpeg | Photos |
| gif | image/gif | Animated images |
| svg | image/svg+xml | Vector graphics |
| ico | image/x-icon | Favicons |
| txt | text/plain | Text files |
| json | application/json | Data interchange |

## 🛣️ API Endpoints

### Available Routes

| Method | Endpoint | Description | Parameters | Example Response |
|--------|----------|-------------|------------|------------------|
| GET | `/hello` | Basic greeting message | None | `"Hello World!"` |
| GET | `/greeting` | Customizable greeting | `name` (optional, default: "World") | `"Hello Diego!"` |
| GET | `/count` | Incrementing counter | None | `"Request count: 5"` |

### Example API Calls

```bash
# Basic greeting
curl http://localhost:35000/hello
# Response: "Hello World!"

# Custom greeting with parameter
curl "http://localhost:35000/greeting?name=Diego"
# Response: "Hello Diego!"

# Counter endpoint (stateful)
curl http://localhost:35000/count
# Response: "Request count: 1"

# Second call to counter
curl http://localhost:35000/count
# Response: "Request count: 2"
```

### Client-Side Integration

```javascript
// Fetch API example
async function testEndpoints() {
    try {
        // Test basic greeting
        const hello = await fetch('http://localhost:35000/hello');
        console.log(await hello.text()); // "Hello World!"
        
        // Test parametrized greeting
        const greeting = await fetch('http://localhost:35000/greeting?name=JavaScript');
        console.log(await greeting.text()); // "Hello JavaScript!"
        
        // Test counter
        const count = await fetch('http://localhost:35000/count');
        console.log(await count.text()); // "Request count: X"
    } catch (error) {
        console.error('API call failed:', error);
    }
}
```

> **⚠️ Note**: The UI includes demonstration buttons for `/api/hello`, `/api/weather`, and `/api/quote`. These endpoints return 404 responses until implemented in your custom controllers.

## 🧪 Comprehensive Testing

### Running Tests

```bash
# Execute all unit tests
mvn test

# Run tests with detailed output
mvn test -X

# Generate coverage report
mvn test jacoco:report

# Run specific test class
mvn test -Dtest=HttpServerTest

# Run tests with Maven Surefire reports
mvn surefire-report:report
```

### Test Results
<div align="center">
  <img src="img/test_result.png" alt="Unit Test Results" width="800"/>
  <p><em>Complete Test Suite Results</em></p>
</div>

### Test Coverage Areas

| Component | Test Focus | Coverage |
|-----------|------------|----------|
| **Request/Response Processing** | HTTP parsing, response generation | ✅ Complete |
| **Controller Discovery** | `ClassScanner` and `RouteInfo` functionality | ✅ Complete |
| **Routing System** | Dynamic endpoint registration and invocation | ✅ Complete |
| **Controller Examples** | `HelloController` and `GreetingController` validation | ✅ Complete |
| **Server Integration** | Basic `HttpServer` functionality and error handling | ✅ Complete |
| **MIME Type Detection** | File extension to content-type mapping | ✅ Complete |
| **Static File Serving** | File system access and content delivery | ✅ Complete |

### Test Architecture

```java
@Test
public void testControllerDiscovery() {
    // Verify annotation scanning
    List<Class<?>> controllers = ClassScanner.findControllers("com.escuelaing.arep.controllers");
    assertFalse(controllers.isEmpty());
    
    // Verify method mapping
    for (Class<?> controller : controllers) {
        Method[] methods = controller.getDeclaredMethods();
        // Test method annotations and routing
    }
}
```

## 📊 API Testing with Postman

Comprehensive endpoint validation performed with Postman Collection:

### GET /hola Endpoint
<div align="center">
  <img src="img/hola_get.png" alt="Postman Test - /hola Endpoint" width="800"/>
  <p><em>Testing Spanish greeting endpoint</em></p>
</div>

### GET /greeting (Default Parameter)
<div align="center">
  <img src="img/greeting_get.png" alt="Postman Test - /greeting Default" width="800"/>
  <p><em>Testing greeting with default parameter</em></p>
</div>

### GET /greeting (Custom Parameters)
<div align="center">
  <img src="img/greeting_get_params.png" alt="Postman Test - /greeting with Parameters" width="800"/>
  <p><em>Testing greeting with custom name parameter</em></p>
</div>

### GET /count Endpoint
<div align="center">
  <img src="img/count_get.png" alt="Postman Test - /count Endpoint" width="800"/>
  <p><em>Testing stateful counter endpoint</em></p>
</div>

### Postman Collection

```json
{
  "info": {
    "name": "WebApp Framework API Tests",
    "description": "Complete test suite for custom HTTP server endpoints"
  },
  "item": [
    {
      "name": "Health Check",
      "request": {
        "method": "GET",
        "url": "{{baseUrl}}/hello"
      }
    },
    {
      "name": "Greeting with Parameters",
      "request": {
        "method": "GET",
        "url": "{{baseUrl}}/greeting?name={{username}}"
      }
    }
  ],
  "variable": [
    {
      "key": "baseUrl",
      "value": "http://localhost:35000"
    }
  ]
}
```

## ⚠️ Current Limitations & Roadmap

### Known Constraints

| Limitation | Impact | Priority |
|------------|--------|----------|
| **Sequential Processing** | One request at a time, no concurrency | 🔴 High |
| **Limited HTTP Methods** | Only GET mapping implemented | 🟡 Medium |
| **Basic File Caching** | No LRU/LFU policies or memory management | 🟡 Medium |
| **No Content Negotiation** | No automatic JSON/XML serialization | 🟡 Medium |
| **JAR Scanning Limitation** | Cannot discover classes within JAR files | 🟢 Low |
| **Development Features** | No hot-reload or template engine | 🟢 Low |

### 🛣️ Future Enhancements

#### Phase 1: Core Improvements
- [ ] **Multi-threaded Request Handling**: ThreadPoolExecutor implementation
- [ ] **Complete HTTP Method Support**: POST, PUT, DELETE, PATCH mappings
- [ ] **Request Body Processing**: JSON/XML deserialization
- [ ] **Response Serialization**: Automatic content-type negotiation

#### Phase 2: Advanced Features
- [ ] **Advanced Caching Strategies**: LRU/LFU cache with configurable limits
- [ ] **Session Management**: Cookie-based session handling
- [ ] **Authentication**: Basic/Bearer token authentication
- [ ] **Rate Limiting**: Request throttling and IP-based limits

#### Phase 3: Enterprise Features
- [ ] **Template Engine Integration**: Thymeleaf or similar
- [ ] **Configuration Management**: YAML/Properties file support
- [ ] **Logging Framework**: SLF4J integration with file rotation
- [ ] **Health Monitoring**: Actuator-like endpoints for monitoring

## 🎓 Learning Outcomes & Skills Demonstrated

This project demonstrates proficiency in:

### Core Java Concepts
- **Advanced Reflection API**: Dynamic class discovery and method invocation
- **Annotation Processing**: Custom annotation framework development
- **Network Programming**: Low-level socket programming and HTTP protocol
- **Design Patterns**: IoC container, dependency injection, and factory patterns

### Software Architecture
- **Enterprise Patterns**: Layered architecture and separation of concerns
- **Microservices Principles**: RESTful API design and stateless services
- **Clean Code**: SOLID principles and maintainable code structure

### DevOps & Deployment
- **Containerization**: Docker image creation and deployment strategies
- **Cloud Deployment**: AWS EC2 configuration and security groups
- **CI/CD Practices**: Maven build lifecycle and automated testing

### Testing & Quality Assurance
- **Unit Testing**: JUnit test suites and test-driven development
- **Integration Testing**: End-to-end API testing with Postman
- **Code Coverage**: Comprehensive test coverage analysis

## 🛠️ Development Setup

### IDE Configuration

#### IntelliJ IDEA
```xml
<!-- Run Configuration -->
<configuration name="HttpServer" type="Application">
  <option name="MAIN_CLASS_NAME" value="com.escuelaing.arep.HttpServer" />
  <option name="VM_PARAMETERS" value="-Xmx512m" />
  <option name="PROGRAM_PARAMETERS" value="" />
  <option name="WORKING_DIRECTORY" value="$PROJECT_DIR$" />
</configuration>
```

#### Visual Studio Code
```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Launch HttpServer",
      "request": "launch",
      "mainClass": "com.escuelaing.arep.HttpServer",
      "projectName": "arep-taller-4"
    }
  ]
}
```

### Project Structure

```
📦 arep-taller-4/
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/com/escuelaing/arep/
│   │   │   ├── 📁 annotations/          # Custom framework annotations
│   │   │   │   ├── GetMapping.java
│   │   │   │   ├── RequestParam.java
│   │   │   │   └── RestController.java
│   │   │   ├── 📁 config/              # Server configuration classes
│   │   │   │   └── ServerConfig.java
│   │   │   ├── 📁 controllers/         # Example REST controllers
│   │   │   │   ├── HelloController.java
│   │   │   │   └── GreetingController.java
│   │   │   ├── 📁 scanner/             # Reflection-based class discovery
│   │   │   │   ├── ClassScanner.java
│   │   │   │   └── RouteInfo.java
│   │   │   └── 📄 HttpServer.java      # Main server implementation
│   │   └── 📁 resources/
│   │       └── 📁 static/              # Static web content
│   │           ├── index.html
│   │           ├── styles.css
│   │           ├── script.js
│   │           └── logo.svg
│   └── 📁 test/                        # Comprehensive unit tests
│       └── 📁 java/com/escuelaing/arep/
│           ├── HttpServerTest.java
│           ├── ClassScannerTest.java
│           └── ControllerTest.java
├── 📄 pom.xml                          # Maven build configuration
├── 📄 Dockerfile                       # Container configuration
├── 📄 docker-compose.yml               # Multi-service orchestration
└── 📄 README.md                        # Project documentation
```

## 🐳 Docker Configuration

### Multi-Stage Dockerfile

```dockerfile
# Build stage
FROM maven:3.9-openjdk-21-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:21-jre-slim
WORKDIR /app
COPY --from=build /app/target/urlobject-1.0-SNAPSHOT.jar app.jar
EXPOSE 35000
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:35000/hello || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Docker Compose Configuration

```yaml
version: '3.8'
services:
  webapp:
    build: 
      context: .
      dockerfile: Dockerfile
    ports:
      - "35000:35000"
    environment:
      - PORT=35000
      - JAVA_OPTS=-Xmx512m
    restart: unless-stopped
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:35000/hello"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 40s
    labels:
      - "traefik.enable=true"
      - "traefik.http.routers.webapp.rule=Host(`webapp.local`)"
```

> **🚨 Important**: The server port is currently hardcoded to `35000` in `ServerConfig.PORT`. The `PORT` environment variable in the Dockerfile is not yet integrated for dynamic port configuration. This is planned for future releases.

## 🤝 Contributing

We welcome contributions! Please see our contributing guidelines:

### How to Contribute

1. **Fork the Repository**
   ```bash
   git fork https://github.com/diegcard-arep/arep-taller-4.git
   ```

2. **Create Feature Branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```

3. **Make Changes and Test**
   ```bash
   mvn clean test
   ```

4. **Commit Changes**
   ```bash
   git commit -m "feat: add amazing feature"
   ```

5. **Push and Create PR**
   ```bash
   git push origin feature/amazing-feature
   ```

### Development Standards
- Follow Java naming conventions
- Maintain test coverage above 80%
- Add Javadoc for public methods
- Use meaningful commit messages (Conventional Commits)

## 📞 Support & Issues

- **Bug Reports**: [GitHub Issues](https://github.com/diegcard-arep/arep-taller-4/issues)
- **Feature Requests**: [GitHub Discussions](https://github.com/diegcard-arep/arep-taller-4/discussions)
- **Documentation**: This README and inline code comments

## 👨‍💻 Author

**Diego Cardenas**
- GitHub: [@diegcard](https://github.com/diegcard)
- LinkedIn: [Diego Cardenas](https://linkedin.com/in/diegcard)
- Email: diego.cardenas@escuelaing.edu.co

## 📄 License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for complete details.

```
MIT License

Copyright (c) 2024 Diego Cardenas

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

---

<div align="center">
  <p><strong>Enterprise Architectures (AREP) - Workshop 3</strong></p>
  <p><em>Escuela Colombiana de Ingeniería Julio Garavito</em></p>
  <p>Academic Year 2024</p>
</div>