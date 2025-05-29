# Hiring Challenge Solution

This is a Spring Boot application that generates a webhook, solves a SQL problem based on a registration number, and submits the solution using a JWT token.

## Prerequisites

- Java 17 or later
- Maven 3.6 or later

## Building the Application

To build the application, run:

```bash
mvn clean package
```

This will create a JAR file in the `target` directory named `hiring-0.0.1-SNAPSHOT.jar`.

## Running the Application

### Using the deployment script

1. Make the script executable:
```bash
chmod +x deploy.sh
```

2. Start the application:
```bash
./deploy.sh start
```

Other commands:
- Check status: `./deploy.sh status`
- Stop application: `./deploy.sh stop`
- Restart application: `./deploy.sh restart`

### Manual execution

```bash
java -jar target/hiring-0.0.1-SNAPSHOT.jar
```

## Project Structure

- `src/main/java/com/example/hiring/` - Main application code
- `src/main/resources/` - Application properties and resources
- `deploy.sh` - Deployment script for managing the application

## Features

- Webhook generation
- SQL problem solving
- JWT token-based solution submission
- Even number problem handling

## Download Links

- GitHub Repository: https://github.com/Sujalbajaj376/bajajfinserv.git
- JAR File: [Will be updated after release creation] 