package com.example.library.book;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
        if(book == null) {
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    //TODO: add create update delete of book service
    public ResponseEntity<Book> saveBook(Book book) {
        Book savedBook = bookRepository.save(book);

        // Build the URI for the created resource
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .replacePath("v1/books/{id}")
            .buildAndExpand(savedBook.getId())
            .toUri();

        return ResponseEntity.created(location).body(savedBook);
    }

}
