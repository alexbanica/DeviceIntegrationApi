FROM maven:3.9.6-amazoncorretto-21 AS build

WORKDIR /workspace

COPY pom.xml .
RUN mvn -B -e dependency:go-offline

COPY . .

RUN mvn -B -e package -DskipTests

FROM amazoncorretto:21.0.0-alpine3.18 AS runtime

RUN addgroup -S app && adduser -S -G app app

WORKDIR /app

COPY --from=build /workspace/target/*.jar app.jar

RUN chown app:app /app/app.jar
USER app

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENV JAVA_OPTS=""

HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
  CMD wget -q --spider http://localhost:8080/actuator/health || wget -q --spider http://localhost:8080/ || exit 1

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar /app/app.jar"]
