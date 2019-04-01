FROM java:8-jre

COPY ./target/user-service-0.0.1.jar /app/
WORKDIR /app
CMD ["java", "-Xmx200m", "-jar", "user-service-0.0.1.jar"]

EXPOSE 18082