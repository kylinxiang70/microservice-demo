FROM java:8-jre

COPY ./target/product-service-0.0.1.jar /app/
WORKDIR /app
CMD ["java", "-Xmx200m", "-jar", "product-service-0.0.1.jar"]

EXPOSE 18083