package com.jumong.librarymanagementsystem.data.repositories;

import com.jumong.librarymanagementsystem.data.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {


    Optional<Token> findByOwnerEmail(String email);

//    Token findTokenByEmail(String email);
}
