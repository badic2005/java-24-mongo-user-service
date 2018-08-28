package com.example.userservicedemo.controller;

import com.example.userservicedemo.model.documents.User;
import com.example.userservicedemo.model.documents.UserCredentials;
import com.example.userservicedemo.model.web.LoginRequest;
import com.example.userservicedemo.model.web.RegistrationRequest;
import com.example.userservicedemo.repository.UserCredentialRepository;
import com.example.userservicedemo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * firstName;
     * lastName;
     * email;
     * password;
     */

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @PostMapping("/register")
    public User register(@RequestBody RegistrationRequest registrationRequest) {
        User user = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .build();

        userRepository.save(user);
        //user получит свой ID после этой строчки

        UserCredentials userCredentials = UserCredentials.builder()
                .user(user)
                .password(registrationRequest.getPassword())
                .build();

        userCredentialRepository.save(userCredentials);

        return user;
    }

    //stateless
    @PostMapping("/login")
    public User login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new RuntimeException("User or password is incorrect");
        }

        UserCredentials userCredentials = userCredentialRepository.findByUserAndPassword(user, loginRequest.getPassword());

        if (userCredentials == null) {
            throw new RuntimeException("User or password is incorrect");
        }

        return user;
    }


//    @PostMapping("/logout")

    //Защищенная секция, куда можно будет обращаться только зарегистрированым юзерам
    @GetMapping("/secure")
    public String securedSection(@RequestHeader("Authorization") String userId) {
        System.out.printf("Header Authorization: %s\n\n\n", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Forbidden"));

        return "Hello logger in user " + user.getFirstName();

    }

    @SneakyThrows
    public static void main(String[] args) {
        ObjectMapper o = new ObjectMapper();
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
                .email("asd")
                .firstName("asd")
                .lastName("asd")
                .password("asdad")
                .build();


        System.out.println(o.writerWithDefaultPrettyPrinter().writeValueAsString(registrationRequest));
    }
}
