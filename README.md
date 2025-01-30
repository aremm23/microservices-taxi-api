# Taxi Service Backend

## Description
This project is a microservices-based backend for a taxi service. It allows users to register, request rides, make payments, and leave reviews.

## Technology Stack
- **Spring Boot** — main framework for microservices development.
- **Docker & Docker Compose** — containerization and orchestration.
- **Kubernetes (Minikube)** — deployment and service management.
- **PostgreSQL, MongoDB, MySQL** — databases.
- **Kafka & RabbitMQ** — message brokers.
- **Redis** — data caching.
- **Keycloak** — user management and authentication.
- **Zipkin** — distributed tracing.
- **Grafana & Prometheus** — monitoring.
- **ELK (Elasticsearch, Logstash, Kibana)** — logging.
- **Stripe** — payment processing.
- **Google Maps API** — maps and routing.

## Microservices and Their Ports
- **auth-service (8081)** — authentication and authorization.
- **passenger-service (8082)** — passenger management.
- **driver-service (8083)** — driver management.
- **ride-service (8084)** — ride processing.
- **review-service (8085)** — review system.
- **payment-service (8086)** — payments.
- **notification-service (8087)** — notifications.

## Deployment
### Running with Docker Compose
```sh
docker-compose up -d
```

### Deploying to Kubernetes (Minikube)
```sh
sh k8s-script.sh
```

## Monitoring and Logging
- **Grafana**: `http://localhost:3000`
- **Kibana**: `http://localhost:5601`
- **Zipkin**: `http://localhost:9411`

## API Documentation
API specifications are available via Swagger:
```sh
http://localhost:<service-port>/<service-name>-docs/swagger-ui
```

## Author
Developer: Artsem Maiseyenka

