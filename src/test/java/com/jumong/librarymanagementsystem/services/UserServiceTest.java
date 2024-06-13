package com.jumong.librarymanagementsystem.services;

import com.jumong.librarymanagementsystem.data.models.User;
import com.jumong.librarymanagementsystem.dtos.request.CreateUserRequest;
import com.jumong.librarymanagementsystem.dtos.request.LoginRequest;
import com.jumong.librarymanagementsystem.dtos.response.CreateUserResponse;
import com.jumong.librarymanagementsystem.dtos.response.LoginResponse;
import com.jumong.librarymanagementsystem.exceptions.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
public class UserServiceTest {

    @Autowired
    private UserServices userService;
    @Test
    public void testRegisterUser(){
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("DummyTwoEmail@gmail.com");
        request.setPassword("password");
        request.setFirstName("Dummy");
        request.setLastName("Two");

        CreateUserResponse response = userService.register(request);
        assertThat(response).isNotNull();
        assertTrue(response.getMessage().contains("Registration successful"));
    }

    @Test
    public void testThatUserCannotRegisterWithSameEmail(){
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("lawaltoheeb36@gmail.com");
        request.setPassword("password");
        request.setFirstName("Lawal");
        request.setLastName("Toheeb");

        assertThrows(UserAlreadyExistsException.class, ()->userService.register(request));
    }

    @Test
    public void testFindUserByEmail(){
        Optional<User> user = userService.findUserByEmail("lawaltoheeb36@gmail.com");
        assertThat(user).isNotNull();
    }

    @Test
    public void testLogin(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("lawaltoheeb36@gmail.com");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userService.login(loginRequest);
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.getMessage()).isNotNull();

    }


}
