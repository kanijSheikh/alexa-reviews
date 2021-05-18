FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} review.jar

RUN mkdir -p /test
WORKDIR /test

COPY /target/review.jar /test/review.jar

USER root

ENTRYPOINT ["java","-jar","/test/review.jar"]

CMD ["java", "-Dspring.data.mongodb.uri=mongodb://localhost:27017/AlexaReviews","-jar","/test/review.jar"]
