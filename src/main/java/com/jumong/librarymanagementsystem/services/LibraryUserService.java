package com.jumong.librarymanagementsystem.services;

import com.jumong.librarymanagementsystem.data.models.User;
import com.jumong.librarymanagementsystem.data.repositories.UserRepository;
import com.jumong.librarymanagementsystem.dtos.request.CreateUserRequest;
import com.jumong.librarymanagementsystem.dtos.request.LoginRequest;
import com.jumong.librarymanagementsystem.dtos.response.CreateUserResponse;
import com.jumong.librarymanagementsystem.dtos.response.LoginResponse;
import com.jumong.librarymanagementsystem.exceptions.InvalidCredentialException;
import com.jumong.librarymanagementsystem.exceptions.UserAlreadyExistsException;
import com.jumong.librarymanagementsystem.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.jumong.librarymanagementsystem.exceptions.ExceptionMessage.USER_ALREADY_EXIST;

@Service
@Slf4j
public class LibraryUserService implements UserServices {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final TokenService tokenService;

    private final EmailServices emailService;
    @Autowired
    public LibraryUserService(UserRepository userRepository, ModelMapper modelMapper, EmailServices emailServices, TokenService tokenService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.emailService = emailServices;
        this.tokenService = tokenService;
    }

    @Override
    public CreateUserResponse register(CreateUserRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        String password = request.getPassword();
        String firstname = request.getFirstName();
        String lastname = request.getLastName();
        if (checkIfEmailExist(email)) throw new UserAlreadyExistsException(USER_ALREADY_EXIST.getMessage());

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        userRepository.save(user);
        String token = tokenService.createToken(email);

        emailService.sendEmail(user.getEmail(), "Confirmation", token);
        CreateUserResponse response = new CreateUserResponse();
        response.setMessage("Registration successful");
        return response;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        log.info("User name ----->{}", email);
        log.info("Password ----->{}", password);
        return checkLoginDetail(email, password);
    }

    private LoginResponse checkLoginDetail(String email, String password) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            if (user.getPassword().equals(password)){
                return loginResponseMapper(user);
            } else {
                throw new InvalidCredentialException("Invalid username or password");
            }
        } else {
            throw new InvalidCredentialException("Invalid username or password");
        }

    }

    private LoginResponse loginResponseMapper(User user) {
        LoginResponse loginResponse = new LoginResponse();
        String accessToken = JwtUtils.generateAccessToken(user.getId());
        BeanUtils.copyProperties(user, loginResponse);
        loginResponse.setJwtToken(accessToken);
        loginResponse.setMessage("Login Successful");


        return loginResponse;
    }

    private boolean checkIfEmailExist(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }



}
