package org.example.service;

import org.example.controller.dto.UserCreateRequest;
import org.example.controller.dto.UserResponse;
import org.example.controller.dto.UserUpdateRequest;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserResponse getUser(String id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));

        UserResponse userResponse = new UserResponse();
        userResponse.id = userEntity.id;
        userResponse.login = userEntity.login;
        userResponse.name = userEntity.name;
        userResponse.lastName = userEntity.lastName;
        userResponse.creationDate = userEntity.creationDate;

        return userResponse;
    }

    public UserResponse createUser(UserCreateRequest user) {
        User userEntity = new User();
        userEntity.id = UUID.randomUUID().toString();
        userEntity.login = user.login;
        userEntity.name = user.name;
        userEntity.lastName = user.lastName;
        userEntity.creationDate = LocalDateTime.now();

        userRepository.save(userEntity);

        UserResponse userResponse = new UserResponse();
        userResponse.id = userEntity.id;
        userResponse.login = userEntity.login;
        userResponse.name = userEntity.name;
        userResponse.lastName = userEntity.lastName;
        userResponse.creationDate = userEntity.creationDate;

        return userResponse;
    }

    public UserResponse updateUser(String id, UserUpdateRequest user) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));

        userEntity.name = user.name;
        userEntity.lastName = user.lastName;

        userRepository.save(userEntity);

        UserResponse userResponse = new UserResponse();
        userResponse.id = userEntity.id;
        userResponse.login = userEntity.login;
        userResponse.name = userEntity.name;
        userResponse.lastName = userEntity.lastName;
        userResponse.creationDate = userEntity.creationDate;

        return userResponse;
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
