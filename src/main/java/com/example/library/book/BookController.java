package com.example.library.book;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("v1/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping({"/", ""})
    public ResponseEntity<List<Book>> findAll() {
        return bookService.findAllBooks();
    }
    
    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity<Book> findBookById(@PathVariable String id) {
        return bookService.findBookById(id);
    }


    
    // @PostMapping({"/create", "/create/"})
    // public ResponseEntity<Book> saveBook(
    //     @RequestParam("id") String id,
    //     @RequestParam("title") String title,
    //     @RequestParam("author") String author,
    //     @RequestParam("publicationDate") String publicationDate,
    //     @RequestParam("genre") String genre,
    //     @RequestParam("description") String description,
    //     @RequestParam("posterImage") String posterImage) {
    //     return bookService.saveBook(new Book(id, title, author, publicationDate, genre, description, posterImage));
    // }

    // TODO: add create update delete of book
    @PostMapping({"/create", "/create/"})
    public ResponseEntity<Book> saveBookJson(@RequestBody Book entity) {
        return bookService.saveBook(entity);
    }
    
    
}
