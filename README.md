# Video Metadata Service

A Spring Boot 3 application (Java 21) for importing video metadata (e.g. from YouTube, Facebook, Vimeo) into PostgreSQL, querying it (with pagination, sorting & filtering), and computing per-source statistics. Security is JWT-backed OAuth2 with two roles: `ROLE_ADMIN` and `ROLE_USER`.

---

## Design Decisions & Assumptions

### Java 21 & Spring Boot 3
Chose Java 21 to leverage the latest language features, performance improvements, and long‑term support. Spring Boot 3 was selected for its compatibility with modern Java versions, streamlined dependency management, and robust ecosystem of starters and auto‑configuration.

### PostgreSQL
Using PostgreSQL 16 for its stability, advanced SQL feature set, JSON support, and widespread adoption. It integrates seamlessly with JPA/Hibernate and provides reliable transaction handling.

### Flyway
Versioned schema migrations with Flyway ensure repeatable, auditable database changes. All migration scripts live under src/main/resources/db/migration, and Flyway is configured to run automatically on startup (baseline-on-migrate: true).

### ULIDs
Entity identifiers use ULIDs (26‑character Crockford Base32) instead of UUIDs or serial keys. ULIDs offer lexicographical sort order, compact representation, and collision resistance without central coordination. A custom Hibernate ULIDGenerator produces these IDs.

### Security: JWT & Roles
OAuth2 resource server with JWT tokens. A custom JwtAuthenticationConverter extracts a single role claim and maps it to Spring authorities (```ROLE_ADMIN, ROLE_USER```). Method security (```@EnableMethodSecurity + @PreAuthorize```) secures endpoints by role.

### Validation
Jakarta Validation annotations (```@NotBlank, @Size, @Pattern```) on DTOs enforce input constraints. Controllers use ```@Valid``` and ```@Validated``` to return 400 Bad Request on violations.

### Pagination & Filtering
List endpoints accept Spring Data Pageable parameters for pagination and sorting. Dynamic filters are built with JPA Specifications from a ```VideoFilterDTO```, enabling optional filters on any field.

### OpenAPI & Swagger
Leverage springdoc-openapi to automatically generate an OpenAPI 3 specification and serve a Swagger UI:
- Documentation endpoints: OpenAPI JSON at ```/v3/api-docs```, Swagger UI at ```/swagger-ui.html``` or ```/swagger-ui/index.html```.
- Annotations: use ```@Operation```, ```@Schema```, ```@ApiResponse``` in controllers/DTOs.
- Security: document bearerAuth scheme and role‐based requirements with ```@SecurityRequirement```.

### Logging
- Controllers log request metadata at DEBUG and business milestones at INFO.
- Services log key operations and caught exceptions with contextual details.
- Hibernate SQL can be enabled (spring.jpa.show-sql) for development diagnostics.

### Docker & Multi‑Stage Builds
Containerized via a multi‑stage Dockerfile: first stage builds the fat JAR with Gradle, second stage runs on a slim JRE image. This minimizes the final image size and attack surface.

### Docker Compose Networking
Compose defines two services (postgres-database and video-metadata-service) on a private network. Service names act as DNS hostnames inside containers, eliminating the need for hardcoded IPs.

---

## Instructions to Run

### 1. Launch with Docker Compose
Build & start both PostgreSQL and the Spring Boot app:

```bash
docker compose up --build -d
```

This will start:
- Postgres on host localhost:5432
- Spring Boot app on host localhost:7000

### 2. Verify the application
Check application health by accessing:

```
http://localhost:7000/actuator/health
```

### 3. Check API documentation
Check API documentation by accessing:

```
http://localhost:7000/docs
```

---

## Authentication & Login
All protected endpoints require an ```Authorization``` header:

```
Authorization: Bearer <JWT_TOKEN>
```

### Log in as ADMIN (already loaded in the application)

```bash
curl --location 'http://localhost:7000/auth/login' \
--header 'Content-Type: application/json' \
--data '{
"username": "john.admin",
"password": "dc872b11dd2089a3edc513b0d5a725e0b41380c180b11b0f9a8784ee714d189b"
}'
```

### Log in as USER (already loaded in the application)

```bash
curl --location 'http://localhost:7000/auth/login' \
--header 'Content-Type: application/json' \
--data '{
"username": "john.user",
"password": "ee76f6e173d84fe789a43a1042d535a423f2c06eaf971f4b1b3556ceea202aac"
}'
```

#### Response (200 OK)

```json
{ "token": "eyJhbGciOiJIUzI1NiJ9.…" }
```

Save the returned token and include it in subsequent requests ```Authorization``` header.

---

## Example API Usage

There is a folder called ```postman``` in the root project which contains a collection and an environment with all the 
following requests. Feel free to import and use them to speed up API testing.

### 1. Import a Video (ADMIN only)
```bash
curl --location 'http://localhost:7000/videos/import' \
--header 'Authorization: Bearer <ADMIN_TOKEN>' \
--header 'Content-Type: application/json' \
--data '{
"url": "https://facebook.com/amazing-unbelievable-amazing"
}'
```
URL pattern: **https://{source}.com/{video-title}**

### 2. Get Video by ID (USER & ADMIN)
```bash
curl --location 'http://localhost:7000/videos/01K147Q7B75128K2GB5FYH4FA4' \
--header 'Authorization: Bearer <USER_OR_ADMIN_TOKEN>'
```
   
### 3. List All Videos (USER & ADMIN)
Supports pagination, sorting, filtering:

```bash
curl --location 'http://localhost:7000/videos?page=0&size=5&sort=uploadDate,durationInSeconds,desc&source=youtube' \
--header 'Authorization: Bearer <USER_OR_ADMIN_TOKEN>'
```

- page: zero-based page index
- size: items per page
- sort: field[,direction] (multiple fields separated by commas)

Filter: any VideoFilterDTO field as query param, e.g. ?source=youtube&title=spring

### 4. Get Statistics for All Sources (USER & ADMIN)
```bash
curl --location 'http://localhost:7000/videos/stats' \
--header 'Authorization: Bearer <USER_OR_ADMIN_TOKEN>'
```