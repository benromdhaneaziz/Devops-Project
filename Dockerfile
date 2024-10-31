# Use an Alpine-based JDK as the base image
FROM openjdk:17-alpine

# Set the working directory
WORKDIR /app

# Copy the Spring Boot jar file into the container
COPY target/DevOps_Project-1.0.jar /app/app.jar

# Expose the application port
EXPOSE 8086

# Run the application
CMD ["java", "-jar", "/app/app.jar"]
