package com.example.library.book;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document(collection = "books")
@Data
@JsonIgnoreType
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private ObjectId _id;
    @Field("id")
    private String id;
    private String title;
    private String author;
    private String publicationDate;
    private String genre;
    private String description;
    private String posterImageName;



    public Book(String id, String title, String author, String publicationDate, String genre, String description, String posterImageName) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.genre = genre;
        this.description = description;
        this.posterImageName = posterImageName;
    }

}
