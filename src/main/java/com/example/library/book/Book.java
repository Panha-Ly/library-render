package com.example.library.book;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document(collection = "books")
@Data
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
    private String posterImageType;
    private byte[] posterImageBytes;
    private String posterImageUrl;

    public Book(String id, String title, String author, String publicationDate, String genre, String description, String posterImageName, String posterImageType, byte[] posterImageBytes, String posterImageUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.genre = genre;
        this.description = description;
        this.posterImageName = posterImageName;
        this.posterImageType = posterImageType;
        this.posterImageBytes = posterImageBytes;
        this.posterImageUrl = posterImageUrl;
        
    }
}

