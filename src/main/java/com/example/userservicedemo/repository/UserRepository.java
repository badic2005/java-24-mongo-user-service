package com.example.userservicedemo.repository;

import com.example.userservicedemo.model.documents.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
}
