package com.example.library.book;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(String id) {
        Book book = bookRepository.findById(id).orElse(null);
        if(book == null) {
            throw new RuntimeException("Book not found");
        }
        return book;
    }
}
