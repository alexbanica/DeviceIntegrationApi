FROM alpine:3.15 AS downloader
ARG GITHUB_REPO
ARG RELEASE_TAG

# Install required tools
RUN apk add --no-cache curl unzip

# Download and extract WordPress
RUN --mount=type=secret,id=GITHUB_AUTH \
    set -eux; \
    auth="$(cat /run/secrets/GITHUB_AUTH)"; \
    curl -o src.zip -fL \
            -H "X-GitHub-Api-Version: 2022-11-28" \
            -H "Accept: application/vnd.github+json" \
            -H "Authorization: Bearer $auth" \
            "${GITHUB_REPO}/zipball/${RELEASE_TAG}"; \
    \
    unzip src.zip -d /tmp && \
    mv /tmp/alexbanica-* /tmp/src && \
    rm src.zip


FROM --platform=linux/arm64 maven:BASE_BUILD_IMAGE_VERSION AS build

WORKDIR /workspace

COPY --from=downloader /tmp/src /workspace

RUN mvn -B -e org.apache.maven.plugins:maven-dependency-plugin:3.6.1:resolve \
              org.apache.maven.plugins:maven-dependency-plugin:3.6.1:resolve-plugins

# Pre-fetch everything needed for offline builds
RUN mvn -B -e org.apache.maven.plugins:maven-dependency-plugin:3.6.1:go-offline

# Build the package (skip tests)
RUN mvn -B -e package -DskipTests

FROM registry.pi.home:5000/java:BASE_IMAGE_VERSION AS runtime

WORKDIR /app
COPY --from=build /workspace/target/*.jar /app/app.jar

RUN chown app:app /app/app.jar
USER app

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENV JAVA_OPTS="-Xms128m -Xmx256m"
ENV JAVA_ENV=""

HEALTHCHECK --interval=30s --timeout=3s --start-period=5m --retries=3 \
  CMD wget -q --spider http://localhost:8080/actuator/health || wget -q --spider http://localhost:8080/ || exit 1

ENTRYPOINT ["sh", "-c", "exec $JAVA_ENV java $JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar /app/app.jar"]
