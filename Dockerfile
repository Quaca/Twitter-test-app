FROM eclipse-temurin

EXPOSE 8080
COPY build/libs/twitter-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]

#RUN mkdir /app




#ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/spring-boot-application.jar"]

#COPY build/libs/twitter-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

#WORKDIR /app
#
#COPY gradlew .
#COPY gradle gradle
#COPY build.gradle .
#RUN ./gradlew dependencies
#
#COPY src srcf
#RUN ./gradlew build unpack -x test
#RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)
