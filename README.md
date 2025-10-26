# AIGateway

AIGateway is a Spring Boot application that serves as an API gateway for multiple AI model providers, providing a unified interface for chat completions.

## Supported Models

- Qwen (Alibaba Cloud)
- Deepseek
- Doubao

## Features

- Unified API interface for multiple AI models
- Swagger/OpenAPI documentation
- RESTful API endpoints
- Easy configuration for different model providers

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven
- Spring Boot 2.x/3.x

### Configuration

The application can be configured through `application.yml`. Key configurations include:

```yaml
server:
  port: 8082

model:
  qwen:
    baseurl: [Your Qwen API Base URL]
    key: [Your API Key]
  deepseek:
    baseurl: [Your Deepseek API Base URL]
    key: [Your API Key]
  doubao:
    baseurl: [Your Doubao API Base URL]
    key: [Your API Key]
```

### API Documentation

The API documentation is available through Swagger UI:
- Swagger UI: http://localhost:8082/swagger-ui.html
- OpenAPI Docs: http://localhost:8082/api-docs

## Building and Running

To build the project:
```bash
./mvnw clean package
```

To run the application:
```bash
./mvnw spring-boot:run
```

Or run the built jar:
```bash
java -jar target/AIGateway-0.0.1-SNAPSHOT.jar
```

## License

[Add your license information here]

## Contributing

[Add contribution guidelines here]
