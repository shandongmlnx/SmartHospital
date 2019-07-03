FROM openjdk:8u212-jdk-stretch

VOLUME /tmp

WORKDIR /usr/local/smart-auth

ADD smart-auth-1.0-SNAPSHOT.jar auth.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","auth.jar"]