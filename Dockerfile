FROM openjdk:11.0.15-oraclelinux7

WORKDIR /app

COPY target/FriendManagement-0.0.1-SNAPSHOT.jar .

CMD java -jar "-Dspring.profiles.active=docker" FriendManagement-0.0.1-SNAPSHOT.jar