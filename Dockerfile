# Use a lightweight OpenJDK image with Maven preinstalled
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the entire backend source code and Maven wrapper into the container
COPY target/wedding-rsvp-api-1.0.0.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the generated JAR file
CMD ["java", "-jar", "app.jar"]