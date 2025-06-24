FROM openjdk:23
WORKDIR /app
COPY src/ /app/
RUN javac *.java
CMD ["java", "Main"]