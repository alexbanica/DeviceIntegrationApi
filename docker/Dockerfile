# Use Maven + JDK 21 to build the application
FROM maven:3.9.6-amazoncorretto-21 AS build

# Set working dir inside the builder image
WORKDIR /workspace

# Copy only the files needed for dependency resolution first to leverage Docker cache
COPY pom.xml .
# If you use a settings.xml or .m2, copy them here as needed
RUN mvn -B -e dependency:go-offline

# Copy the full project
COPY .. .

# Build the project (skip tests for faster image builds; remove -DskipTests if you want tests run)
RUN mvn -B -e package -DskipTests

# Use a smaller JRE-based image for runtime
FROM amazoncorretto:21.0.0-alpine3.18 AS runtime

# Create a non-root user for improved security (optional)
RUN addgroup -S app && adduser -S -G app app

WORKDIR /app

# Copy the Spring Boot fat jar from the builder stage
# Adjust the path if your built jar name differs. This assumes Maven's standard target/*.jar
COPY --from=build /workspace/target/*.jar app.jar

# Make sure the jar is readable
RUN chown app:app /app/app.jar
USER app

# Set production profile by default
ENV SPRING_PROFILES_ACTIVE=prod

# Expose port used by Spring Boot
EXPOSE 8080

# Allow overriding JVM options at runtime (e.g. -Xms -Xmx)
ENV JAVA_OPTS=""

# Healthcheck (optional) - checks that the app responds on port 8080
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
  CMD wget -q --spider http://localhost:8080/actuator/health || wget -q --spider http://localhost:8080/ || exit 1

# Default command: pass any additional args via docker run after the image name
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar /app/app.jar"]
