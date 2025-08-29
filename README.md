<<<<<<< HEAD
# tbta
=======
# EUROPACE Token-Based Todo App

This mono-repo contains two containerized microservices:
- **User Service**: Handles user registration, login, and token verification.
- **Todo Service**: Manages todos for authenticated users.

## Project Structure
```
/
├── user-service/
│   ├── src/
│   ├── build.gradle
│   └── Dockerfile
├── todo-service/
│   ├── src/
│   ├── build.gradle
│   └── Dockerfile
├── docker-compose.yml
├── settings.gradle
├── build.gradle
└── README.md
```

## Endpoints
### User Service
- `POST /register` — Register a new user (username, password)
- `POST /login` — Authenticate and receive a token
- `POST /token` — Verify a token

### Todo Service
- `POST /todos` — Add a todo (requires Authorization token)
- `GET /todos` — Get all todos for authenticated user (requires Authorization token)

## Authentication
- Tokens are returned by the User Service and must be sent in the `Authorization` header for Todo Service requests.
- Passwords are stored in plain text (for demo/testing purposes).

## Running Locally (Debug)
You can run both services locally for development and debugging:
- **User Service**: Default port 8081
- **Todo Service**: Default port 8082

You can start each service individually using your IDE (e.g., IntelliJ) or via Gradle:
```
./gradlew :user-service:bootRun
./gradlew :todo-service:bootRun
```

## Building & Deploying with Docker Compose
To build and run both services together:
```
docker-compose up --build
```
This will build both images and start the containers on their respective ports.

## Testing
You can use `curl` or Postman to test the endpoints. Here are some example `curl` commands:
### Register a User
```
curl --location 'localhost:8081/register' \
--header 'Content-Type: application/json' \
--data '{
"username": "admin",
"password": "admin"
}'
```



## Dependencies
- The root project has minimal dependencies, only what is required for Gradle and subproject management.
- Each service manages its own dependencies in its own `build.gradle`.

>>>>>>> master
