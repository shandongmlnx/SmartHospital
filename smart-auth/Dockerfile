FROM openjdk:8u212-jdk-stretch
VOLUME /tmp
ADD /target/smart-auth-1.0-SNAPSHOT.jar auth.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","auth.jar"]