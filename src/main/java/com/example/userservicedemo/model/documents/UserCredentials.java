package com.example.userservicedemo.model.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

@Document
public class UserCredentials {

    @Id
    private String id;

    @DBRef
    @Indexed(unique = true)
    private User user; //в mongo хранится ссылка на документ User

    private String password;
}
