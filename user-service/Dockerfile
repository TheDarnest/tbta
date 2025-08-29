# ---- Build Stage ----
FROM gradle:8.5-jdk17-alpine AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# ---- Run Stage ----
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
