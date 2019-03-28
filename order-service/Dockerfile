FROM java:8-jre

COPY ./target/order-service-0.0.1.jar /app/
WORKDIR /app
CMD ["java", "-Xmx200m", "-jar", "order-service-0.0.1.jar"]

EXPOSE 18081