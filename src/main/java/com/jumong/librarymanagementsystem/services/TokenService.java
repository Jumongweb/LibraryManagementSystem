package com.jumong.librarymanagementsystem.services;

import com.jumong.librarymanagementsystem.data.models.Token;

public interface TokenService {

    String  createToken(String email);

    Token findByUserEmail(String email);

    void deleteToken(Long id);


}
