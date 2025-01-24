package com.example.library.book;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Random;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public ResponseEntity<List<Book>> findAllBooks() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    public ResponseEntity<Book> findBookById(String id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }
    public ResponseEntity<Book> saveBook(Book book, MultipartFile posterImageFile) {
        // Generates a new image name
        Random random = new Random();
        int randomNumber = random.nextInt(9000000) + 1000000;
        String fileName = "img" + book.getId() + "_" + String.format("%07d", randomNumber);
        String fileType = posterImageFile.getContentType();

        // Check if file type is null
        if (fileType == null) {
            throw new RuntimeException("No file submited");
        }
        if (fileType.equals("image/jpeg")) {
            fileName += ".jpg";
        } else if (fileType.equals("image/png")) {
            fileName += ".png";
        } else {
            // Handle other file types or throw an exception
            throw new RuntimeException("Unsupported file type: " + fileType);
        }

        // Set the book poster image details
        book.setPosterImageType(fileType);
        book.setPosterImageName(fileName);
        book.setPosterImageUrl("v1/books/" + book.getId() + "/poster");
        try {
            book.setPosterImageBytes(posterImageFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Save the book
        Book savedBook = bookRepository.save(book);

        // Build the URI for the created resource
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("v1/books/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedBook);
    }

    public ResponseEntity<Book> updateBookById(String id, Book entity, MultipartFile posterImageFile) {
        Book existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook == null) {
            return ResponseEntity.notFound().build();
        }
        // Update the book details (except for the poster image)
        existingBook.setTitle(entity.getTitle());
        existingBook.setAuthor(entity.getAuthor());
        existingBook.setPublicationDate(entity.getPublicationDate());
        existingBook.setGenre(entity.getGenre());
        existingBook.setDescription(entity.getDescription());

        // Check if a new poster image is provided
        if (posterImageFile != null && !posterImageFile.isEmpty()) {
            // Generated a new image name
            Random random = new Random();
            int randomNumber = random.nextInt(9000000) + 1000000;
            String fileName = "img" + entity.getId() + "_" + String.format("%07d", randomNumber);
            String fileType = posterImageFile.getContentType();

            // check if file type is null
            if (fileType == null) {
                throw new RuntimeException("No file submited");
            }
            if (fileType.equals("image/jpeg")) {
                fileName += ".jpg";
            } else if (fileType.equals("image/png")) {
                fileName += ".png";
            } else {
                // Handle other file types or throw an exception
                throw new RuntimeException("Unsupported file type: " + fileType);
            }

            // Set the new book poster image details
            existingBook.setPosterImageType(fileType);
            existingBook.setPosterImageName(fileName);
            existingBook.setPosterImageUrl("v1/books/" + entity.getId() + "/poster");
            try {
                existingBook.setPosterImageBytes(posterImageFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return ResponseEntity.ok().body(bookRepository.save(existingBook));
    }

        
    public ResponseEntity<Book> deleteBookById (String id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }
        bookRepository.delete(book);
        return new ResponseEntity<Book>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<byte[]> findPosterById(String id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (book.getPosterImageBytes() == null || book.getPosterImageName() == null || book.getPosterImageType() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(book.getPosterImageType())) 
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + book.getPosterImageName() + "\"")
                .body(book.getPosterImageBytes()); 
    }






    // Methods for 
    // 1. Saving books to the database and its poster image to the local file system. Not storing them together.
    // 2. Fetching book poster image from the local file system.
    // See the Contributing and Improvements and More section of the README.md file for reasons why i didn't use this.

    /**
     * Saves a book and its image to the local file system. The image is saved to the
     * "src/main/resources/static/upload" directory. The image name is a combination of the
     * book's ID and a random number. The book is then saved to the database.
     *
     * @param book   The book object to be saved. The book's posterImageName is updated with
     *               the generated image name.
     * @param image  The image to be saved.
     * @return A ResponseEntity containing the saved book. The HTTP status code is 201 if
     *         the book is saved successfully. The response body contains the saved book and
     *         the location of the book in the response header. If the image is not saved
     *         successfully, the HTTP status code is 500 (Internal Server Error). If the
     *         image type is not supported, the HTTP status code is 400 (Bad Request).
     */
    // Note: if you want to use this method, you need to remove the imagePoster fields on the Book class.
    // you don't need it because the image is saved to the local file system.
    public ResponseEntity<Book> saveBookLocally(Book book, MultipartFile image) {

        // New generated image name
        Random random = new Random();
        int randomNumber = random.nextInt(9000000) + 1000000;
        String fileName = "img" + book.getId() + "_" + String.format("%07d", randomNumber);
        String fileType = image.getContentType();

        // check if file type is null
        if (fileType == null) {
            throw new RuntimeException("No file submited");
        }
        if (fileType.equals("image/jpeg")) {
            fileName += ".jpg";
        } else if (fileType.equals("image/png")) {
            fileName += ".png";
        } else {
            // Handle other file types or throw an exception
            throw new RuntimeException("Unsupported file type: " + fileType);
        }

        // Upload directory path
        Path uploadDirPath = Paths.get("src", "main", "resources", "static", "upload");

        // Saving the image to the directory
        try {
            if (!uploadDirPath.toFile().exists()) {
                uploadDirPath.toFile().mkdirs();
            }
            Files.copy(image.getInputStream(), uploadDirPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<Book>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        book.setPosterImageName(fileName);
        Book savedBook = bookRepository.save(book);

        // Build the URI for the created resource
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("v1/books/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();

        // print the uri
        // System.out.println(location);

        return ResponseEntity.created(location).body(savedBook);
    }

    /**
     * Finds the book poster by its ID and returns it as a ResponseEntity containing a Resource.
     * The resource is a URLResource pointing to the local file system, where the image is stored.
     * The HTTP status code is 200 if the book is found and the image is found on the local file
     * system. The HTTP status code is 404 if the book is not found. The HTTP status code is 500
     * if there is an error reading the image from the local file system.
     * @param id The ID of the book whose poster is to be found.
     * @return A ResponseEntity containing the book poster as a Resource.
     */
    // Note: if you want to use this method, you need to save the image locally.
    // and make sure the method in the BookController class that calls this method return a response entity of type Resource.
    public ResponseEntity<Resource> findLocalPosterById(String id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
  
        try { 
            Path filePath = Paths.get("src", "main", "resources", "static", "upload").resolve(book.getPosterImageName()).normalize(); 
            Resource resource = new UrlResource(filePath.toUri()); 
            if (!resource.exists()) { 
                return ResponseEntity.notFound().build(); 
            } 
            // Determine the file's content type (should be png or jpg)
            String contentType = Files.probeContentType(filePath); 
      
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType)) 
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource); 
        } 
        catch (IOException e) {
            e.printStackTrace(); 
            return ResponseEntity.status(500).build();

        }
    }


}
