package com.example.ece651.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@Document(collection = "comment")
public class Comment {

    @MongoId
    private ObjectId id;

    private String username;
    @DBRef
    private Media avatar;
    private String comment;
    private Date time_created;
    @DBRef
    private List<Object> likes;
    @DBRef
    private List<String> replies;

    public Comment(){
    }

    public Comment(String username, Media avatar, String comment) {
        this.username = username;
        this.avatar = avatar;
        this.comment = comment;
        this.id = new ObjectId();
        this.time_created = new Date();
    }
}
