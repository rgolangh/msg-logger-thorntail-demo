FROM java:openjdk-8-jdk

ADD target/demo-thorntail.jar /opt/thorntail.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/thorntail.jar"]
