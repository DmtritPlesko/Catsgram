FROM amazoncorretto:21
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
ENV CATSGRAM_IMAGE_DIRECTORY = "image_dir"
