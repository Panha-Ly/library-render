package com.example.library.book;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("v1/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Book API
    @GetMapping({ "/", "" })
    public ResponseEntity<List<Book>> findAll() {
        return bookService.findAllBooks();
    }

    @GetMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<Book> findBookById(@PathVariable String id) {
        return bookService.findBookById(id);
    }
    
    @PostMapping({ "/create", "/create/" })
    public ResponseEntity<Book> saveBook(
            @RequestParam("id") String id,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("publicationDate") String publicationDate,
            @RequestParam("genre") String genre,
            @RequestParam("description") String description,
            @RequestParam("posterImageFile") MultipartFile posterImageFile) {

        // create book object without poster info for now, will add it in the service
        Book entity = new Book(id, title, author, publicationDate, genre, description, null, null, null, null);
        return bookService.saveBook(entity, posterImageFile);
    }

    @PutMapping({ "/{id}/update", "/{id}/update/" })
    public ResponseEntity<Book> updateBook(
        @PathVariable String id,
        @RequestParam("title") String title,
        @RequestParam("author") String author,
        @RequestParam("publicationDate") String publicationDate,
        @RequestParam("genre") String genre,
        @RequestParam("description") String description,
        @RequestParam(value = "posterImageFile", required = false) MultipartFile posterImageFile) {
        
        // create book object without poster info for now, will add it in the service
        Book entity = new Book(id, title, author, publicationDate, genre, description, null, null, null, null);
        return bookService.updateBookById(id, entity, posterImageFile);
    }

    @DeleteMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<Book> deleteBook(@PathVariable String id) {
        return bookService.deleteBookById(id);
    }


    // Poster API
    @GetMapping({"/{id}/poster", "/{id}/poster/"})
    public ResponseEntity<byte[]> findPoster(@PathVariable String id) {
        return bookService.findPosterById(id);
    }
    
}
