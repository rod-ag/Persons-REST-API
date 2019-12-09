# Persons-REST-API
A REST API for Person resource

Features:
- GET /api/personas endpoint (get All)
- GET /api/personas/{id} endpoint (find by Id)
- POST /api/personas endpoint (create resource)
- PUT /api/personas endpoint (update resource)
- DELETE /api/personas/{id} endpoint (delete resource)
- POST,PUT,DELETE: Secured REST endpoints with Spring Security OAuth2. AuthorizationServer and ResourceServer implementation
- Swagger UI documentation with OAuth2 password flow grant type authorization
- Respects Hateoas constraint (RESTful)
- Persistence layer implementation using spring-data-jpa and MySQL
- Unit tests of REST endpoints with SpringBootTest and MockMvc
