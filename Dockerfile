FROM openjdk:slim

WORKDIR /app

COPY target/red_mad_robot_test-0.0.1.jar red_mad_robot_test.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","red_mad_robot_test.jar"]