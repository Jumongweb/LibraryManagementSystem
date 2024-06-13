package com.jumong.librarymanagementsystem.services;

public interface EmailServices {
    void sendEmail(String to, String subject, String content);
}
