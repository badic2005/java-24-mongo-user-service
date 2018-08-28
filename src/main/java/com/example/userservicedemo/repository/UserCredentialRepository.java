package com.example.userservicedemo.repository;

import com.example.userservicedemo.model.documents.User;
import com.example.userservicedemo.model.documents.UserCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserCredentialRepository extends MongoRepository<UserCredentials, String> {

    //List<UserCredentials>
    UserCredentials findByUserAndPassword(User user, String password);
}
