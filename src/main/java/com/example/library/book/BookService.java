package com.example.library.book;

import java.io.File;
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

    public ResponseEntity<Book> saveBook(Book book, MultipartFile image) {

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

    public ResponseEntity<Resource> findPosterByName(String posterImageName) {
        Book book = bookRepository.findByPosterImageName(posterImageName).orElse(null);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
  
        try { 
            Path filePath = Paths.get("src", "main", "resources", "static", "upload").resolve(posterImageName).normalize(); 
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

    //TODO: add update delete of book service

}
