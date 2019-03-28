FROM java:8-jre

COPY ./target/auth-center-0.0.1.jar /app/
WORKDIR /app
CMD ["java", "-Xmx200m", "-jar", "auth-center-0.0.1.jar"]

EXPOSE 18080