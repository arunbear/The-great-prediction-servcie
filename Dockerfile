# Build in two stages so it can be run without Java/Maven being installed locally

FROM maven:3.9.9-amazoncorretto-21 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

# uncomment to see files built by mvn
# RUN find /usr/src/app

from openjdk:21
COPY --from=build /usr/src/app/target/great-prediction-service-0.0.1-SNAPSHOT.jar /usr/app/great-prediction-service-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/usr/app/great-prediction-service-0.0.1-SNAPSHOT.jar"]