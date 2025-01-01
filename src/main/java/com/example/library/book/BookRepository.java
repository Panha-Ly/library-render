package com.example.library.book;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, ObjectId> {

}