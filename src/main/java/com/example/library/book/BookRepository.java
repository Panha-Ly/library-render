package com.example.library.book;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, ObjectId> {

    Optional<Book> findById(String id);
}