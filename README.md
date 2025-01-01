# library-render
This is a simple api for fetching data from MongoDB on MongoDB Atlas to your front-end appplication using Spring Boot.

# How to run this project locally
1. pull this project to your PC.
2. create a .env file to store your MongoDB credentials using the .env.example file provided. (you need a database hosted on MongoDB Altas)
3. run this project locally or in a docker container
   
   3.1.   Locally: using Maven command: ```mvn spring-boot:run``` and go to "localhost:8080/"
   
   3.2.   In Docker using dockerfile provided:
     * build the image with docker command: ```docker build -t my-app:latest .```
     * run the image in a docker container and map the port to your specific port: ```docker run -d -p {your-port}:8080 --name my-spring-app my-app:latest```
     * go to "localhost:{your-port}/"
