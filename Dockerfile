FROM maven:3.8.3-openjdk-17
COPY src /app/src
COPY pom.xml /app/pom.xml
RUN  /bin/bash -c 'mvn clean install -DskipTests -f ./app/pom.xml -Dactive.profile=dev'
EXPOSE 8080
ENTRYPOINT ["java","-jar","app/target/Louise-0.0.1-SNAPSHOT.jar"]