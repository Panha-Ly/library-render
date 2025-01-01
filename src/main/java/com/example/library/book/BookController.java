package com.example.library.book;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@RestController
@RequestMapping("v1/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    
    @GetMapping({"/", ""})
    @ResponseBody
    public List<Book> findAll() {
        return bookService.findAllBooks();
    }
    
    @GetMapping({"/{id}", "/{id}/"})
    public Book findBookById(@PathVariable String id) {
        return bookService.findBookById(id);
    }

    
}
