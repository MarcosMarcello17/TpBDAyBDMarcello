FROM openjdk:17-alpine
MAINTAINER 4softwaredevelopers.com
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} TpBDAvanzadaMarcello-0.0.1.jar
ENTRYPOINT ["java","-jar","/TpBDAvanzadaMarcello-0.0.1.jar"]