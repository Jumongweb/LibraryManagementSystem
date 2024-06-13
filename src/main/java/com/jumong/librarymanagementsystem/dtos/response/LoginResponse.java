package com.jumong.librarymanagementsystem.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {

    private String jwtToken;
    private String message;
}
