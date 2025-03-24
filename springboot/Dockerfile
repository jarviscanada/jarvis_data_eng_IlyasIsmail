#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
COPY src /src
COPY pom.xml /
RUN mvn -f /pom.xml clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-oracle
COPY --from=build /target/trading-0.0.1-SNAPSHOT.jar /usr/local/app/trading/lib/trading_app.jar
ENTRYPOINT ["java","-jar","/usr/local/app/trading/lib/trading_app.jar"]