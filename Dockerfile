FROM maven:3.9.16-eclipse-temurin-26-alpine AS build

WORKDIR /workspace
COPY pom.xml ./
RUN mvn -B -ntp dependency:go-offline

COPY src ./src
RUN mvn -B -ntp -DskipTests package


# Runtime stage to avoid leaving the build stage artifacts in the final image
FROM eclipse-temurin:26-jre-alpine

RUN apk add --no-cache curl
WORKDIR /app
COPY --from=build /workspace/target/portaltrip-0.0.1-SNAPSHOT.war app.war

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.war"]
