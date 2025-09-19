FROM alpine:3.15 AS pigpio-builder
# Install build tools and all dependencies needed for compilation
RUN apk add --no-cache \
    git \
    build-base \
    python3-dev

RUN echo -e '#!/bin/sh\nexit 0' > /usr/local/bin/ldconfig && chmod +x /usr/local/bin/ldconfig


# Download the pigpio source and compile it
RUN git clone https://github.com/joan2937/pigpio.git /tmp/pigpio && \
    cd /tmp/pigpio && \
    make && \
    make install || true && \
    ln -sf /usr/local/lib/libpigpio.so.1 /usr/lib/libpigpio.so && \
    ln -sf /usr/local/lib/libpigpiod_if.so.1 /usr/lib/libpigpiod_if.so && \
    ln -sf /usr/local/lib/libpigpiod_if2.so.1 /usr/lib/libpigpiod_if2.so


FROM alpine:3.15 AS downloader
ARG GITHUB_REPO
ARG RELEASE_TAG

# Install required tools
RUN apk add --no-cache curl unzip

# Download and extract
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

# Install Python3 and pip for runtime environment
RUN apk add --no-cache python3 py3-pip && \
    pip3 install pigpio


# Copy pigpio binaries from the Debian build stage
COPY --from=pigpio-builder /usr/local/bin/pigpiod /usr/bin/pigpiod
COPY --from=pigpio-builder /usr/local/lib/libpigpio* /usr/lib/
COPY --from=pigpio-builder /usr/local/include/pigpio.h /usr/include/

# Ensure necessary permissions for Pigpio
RUN chmod +x /usr/bin/pigpiod

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENV JAVA_OPTS=""
ENV JAVA_ENV=""

ENTRYPOINT ["sh", "-c", "pigpiod && exec $JAVA_ENV java $JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar /app/app.jar"]
