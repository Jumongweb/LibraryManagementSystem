package com.jumong.librarymanagementsystem.services;

import com.jumong.librarymanagementsystem.data.models.User;
import com.jumong.librarymanagementsystem.dtos.request.CreateUserRequest;
import com.jumong.librarymanagementsystem.dtos.request.LoginRequest;
import com.jumong.librarymanagementsystem.dtos.response.CreateUserResponse;
import com.jumong.librarymanagementsystem.dtos.response.LoginResponse;

import java.util.Optional;

public interface UserServices {
    CreateUserResponse register(CreateUserRequest request);

    Optional<User> findUserByEmail(String email);

    LoginResponse login(LoginRequest loginRequest);

}
