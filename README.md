# library-render

This is a simple library API for fetching book data from MongoDB on MongoDB Atlas to your front-end application using Spring Boot.

## Table of Contents

1. [How to run this project on your PC](#how-to-run-this-project-on-your-pc)
2. [API Endpoints](#api-endpoints)
3. [Project Structure](#project-structure)
4. [Contribution, Improvements and More](#contribution-improvements-and-more)
5. [License](#license)

## How to run this project on your PC

1. Create a `.env` file to store your MongoDB credentials using the `.env.example` file provided. *(You will need a [MongoDB](https://www.mongodb.com/) database hosted on MongoDB Altas)*
2. Run this project locally or in a Docker container

   ### Locally

   [Maven](https://maven.apache.org/) is required to run this project locally. You can use the following command to run the project locally:

    ```shell
    mvn spring-boot:run
    ```

    Go to `localhost:8080/`

    *Note: Some IDEs (like IntelliJ, [VSCode](https://code.visualstudio.com/), Eclipse etc) provide a way to run this project locally without Maven installed. You just need to install the Spring Boot plugin/extension on your IDE and run the project from the IDE.*

   ### In Docker

   [Docker](https://www.docker.com/) is required to run this project in a Docker container.

    Using the Dockerfile provided:
    - Build the image with the following command:

    ```shell
    docker build -t my-app:latest .
    ```

    - Run the image in a Docker container and map the port to your specific port:

    ```shell
    docker run -d -p {your-port}:8080 --name my-spring-app my-app:latest
    ```

    Go to `localhost:{your-port}/`

## API Endpoints

Note: If you don't want to use `curl` to test the endpoints, you can use the example forms provided in the `example-forms` folder.

Here are all the endpoints and their functionalities:

1. **GET v1/books**
    - Description: Fetches a list of all books.
    - Example Request:

    ```shell
    curl -X GET "http://localhost:8080/v1/books"
    ```

    - Example Response:

    ```json
    [
        {
            "_id": {
                "timestamp": 1737544388,
                "date": "2025-01-22T11:13:08.000+00:00"
            },
            "id": "1",
            "title": "Book Title",
            "author": "Author Name",
            "publicationDate": "Publication Date",
            "genre": "Book Genre",
            "description": "Book Description",
            "posterImageName": "Book Poster Name",
            "posterImageType": "Poster Image Type",
            "posterImageBytes": "Poster Bytes Data",
            "posterImageUrl": "Poster URL"
        },
        {
            "..."
        }
    ]
    ```

2. **GET v1/books/`{id}`**
    - Description: Fetches a specific book by its ID.
    - URL Parameter: `id` (The ID of the book to fetch)
    - Example Request:

    ```shell
    curl -X GET "http://localhost:8080/v1/books/11"
    ```

    - Example Response:

    ```json
    {
        "_id": {
            "timestamp": 1737544388,
            "date": "2025-01-22T11:13:08.000+00:00"
        },
        "id": "11",
        "title": "Book Title",
        "author": "Author Name",
        "publicationDate": "Publication Date",
        "genre": "Book Genre",
        "description": "Book Description",
        "posterImageName": "Book Poster Name",
        "posterImageType": "Poster Image Type",
        "posterImageBytes": "Poster Bytes Data",
        "posterImageUrl": "Poster URL"
    }
    ```

3. **POST v1/books/create**
    - Description: Adds a new book.
    - Example Request:

    ```shell
    curl -X POST "http://localhost:8080/v1/books/create" -H "Content-Type: multipart/form-data" -F "id=18" -F "title=Book Title" -F "author=Author Name" -F "publicationDate=Publication Date" -F "genre=Book Genre" -F "description=Book Description" -F "file=@path/to/your/poster-image.png"
    ```

    - Example Response:

    ```json
    {
        "_id": {
            "timestamp": 1737727748, 
            "date": "2025-01-24T14:09:08.000+00:00"
        },
        "id": "18",
        "title": "Book Title",
        "author": "Author Name",
        "publicationDate": "Publication Date",
        "genre": "Book Genre",
        "description": "Book Description",
        "posterImageName": "img18_3228187.png",
        "posterImageType": "image/png",
        "posterImageBytes": "iVBORw0KGgoAAAAN...",
        "posterImageUrl": "v1/books/18/poster",
    }
    ```

4. **PUT v1/books/`{id}`/update**
    - Description: Updates an existing book by its ID.
    - URL Parameter: `id` (The ID of the book to update)

   ### Scenario 1: Update Text Fields Only

    - Request: Update the title, author, publicationDate, genre, and description. No poster image file is included.
    - Example Request:

    ```shell
    curl -X PUT "http://localhost:8080/v1/books/18/update" -H "Content-Type: multipart/form-data" -F "title=Updated Title" -F "author=Updated Author" -F "publicationDate=Updated Publication Date" -F "genre=Updated Genre" -F "description=Updated Description"
    ```

    - Example Response:

    ```json
    {
        "_id": {
            "timestamp": 1737727748,
            "date": "2025-01-24T14:09:08.000+00:00"
        },
        "id": "18",
        "title": "Updated Title",
        "author": "Updated Author",
        "publicationDate": "Updated Publication Date",
        "genre": "Updated Genre",
        "description": "Updated Description",
        "posterImageName": "img18_3228187.png",
        "posterImageType": "image/png",
        "posterImageBytes": "iVBORw0KGgoAAAAN...",
        "posterImageUrl": "v1/books/18/poster"
    }
    ```

   ### Scenario 2: Update Text Fields and Poster Image

    - Request: Update the title, author, publicationDate, genre, description, and poster image file.
    - Example Request:

    ```shell
    curl -X PUT "http://localhost:8080/v1/books/18/update" -H "Content-Type: multipart/form-data" -F "title=Updated Title" -F "author=Updated Author" -F "publicationDate=Updated Publication Date" -F "genre=Updated Genre" -F "description=Updated Description" -F "file=@path/to/your/new/poster-image.png"
    ```

    - Example Response:

    ```json
    {
        "_id": {
            "timestamp": 1737727748,
            "date": "2025-01-24T14:09:08.000+00:00"
        },
        "id": "18",
        "title": "Updated Title",
        "author": "Updated Author",
        "publicationDate": "Updated Publication Date",
        "genre": "Updated Genre",
        "description": "Updated Description",
        "posterImageName": "new_img18_3228187.png",
        "posterImageType": "image/png",
        "posterImageBytes": "new_iVBORw0KGgoAAAAN...",
        "posterImageUrl": "v1/books/18/poster"
    }
    ```

5. **DELETE v1/books/`{id}`**
    - Description: Deletes a book by its ID.
    - URL Parameter: `id` (The ID of the book to delete)
    - Example Request:

    ```shell
    curl -X DELETE "http://localhost:8080/v1/books/18"
    ```

    - Example Response:

    ```http
    HTTP/1.1 204 No Content
    ```

6. **GET v1/books/`{id}`/poster**
    - Description: Retrieves the poster image of a book by its ID.
    - URL Parameter: `id` (The ID of the book)
    - Example Request:

    ```shell
    curl -X GET "http://localhost:8080/v1/books/18/poster"
    ```

    - Example Response:

    ```http
    HTTP/1.1 200 OK
    Content-Type: image/png
    Content-Disposition: inline; filename="img18_3228187.png"

    iVBORw0KGgoAAAAN... [binary image data]
    ```

    - Example of the process of converting a binary image data to a blob image with Javascript:

    ```js
    // HTML image element
    const img = document.getElementById('poster');

    // Fetch the image
    const response = await fetch('http://localhost:8080/v1/books/18/poster');

    // Check if the request was successful
    if (!response.ok) {
        console.error('Failed to fetch image');
        return;
    }

    // Convert the binary image data to a blob, create a URL for the blob, and set the image source of the HTML image element to the blob URL.
    const imageBlob = await response.blob();
    const imageUrl = URL.createObjectURL(imageBlob);
    img.setAttribute('src', imageUrl);
    ```

    - The Image should look like this:

    ![picture example](https://picsum.photos/id/10/150/200)

## Project Structure

A brief overview of the project structure here.
I only include important files and folders:

```text
/example-forms
/src
  /main
    /java
      /com/example/library
        /book
        /config
        /utils
        /LibraryApplication.java
        /resources 
          /.env.example
          /.env
          /application.properties
          /data-mongo.json
/Dockerfile
/pom.xml
```

Explaination:

- **example-forms folder**: contains example forms (to select, create, update, delete data) for testing the API.
- **book folder**: feature-based folder structure contains the feature "Book" and its controller, service, and repository.
- **config folder**: contains a configuration class for setting up CORS (Cross-Origin Resource Sharing) settings, allowing front-end to send requests this API.
- **utils folder**: contains a controller class for error handling during API requests.
- **LibraryApplication.java file**: the main entry point for the Spring Boot application.
- **.env.example file**: contains the environment variables with *no values* for storing MongoDB credentials. *(rename this file to `.env` to use it or just copy the variables to the `.env` file that you created)*
- **.env file**: contains MongoDB credentials as environment variables.
- **application.properties file**: contains configuration properties for Spring Boot.
- **data-mongo.json file**: contains sample data for MongoDB.
- **Dockerfile file**: contains the instructions for building the Docker image.
- **pom.xml file**: contains the project information and dependencies.

## Contribution, Improvements and More

1. No contribution is accepted at this moment. but you can use this project for free.

2. There are some improvements that can be made in the future for this API project:

    - Implement the usages of JWT for request authentications.
    - Implement data validation for requests with Spring Validation.
    - Implement data transfer objects (DTOs) for data exchange between the API and end-users.
    - Implement better error handling for API requests than i did.
    - Implement a way to store the book poster image on image hosting sites like [Imgur](https://imgur.com/) instead of storing it with the book data in the database.

3. There are some methods in the *BookService.java* file that can be use to store the book poster image to the local file system and fetch the image back from the local file system. I was planning to use these methods but the server i was using *(render.com [free tier](https://render.com/docs/free))* to host this API have some limitations. After a while, the server will spin down due to inactivity and they also don't support persistent disks *(meaning the poster image data will be deleted after the server is down)*. If you want to use these methods, you may need a server that supports persistent disks *(usually not free)*.

## License

MIT License, Copyright (c) 2024 panha21

more in LICENSE file.
