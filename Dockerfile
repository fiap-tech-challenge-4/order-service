FROM maven:eclipse-temurin AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/target/order-service-0.0.1-SNAPSHOT.jar app.jar

RUN jar tf app.jar | grep META-INF/MANIFEST.MF && \
    jar xf app.jar META-INF/MANIFEST.MF && \
    cat META-INF/MANIFEST.MF

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]