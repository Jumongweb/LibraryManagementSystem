package com.jumong.librarymanagementsystem.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JavaMailRequest {

   private String to;
    private String subject;
    private String content;
    private String message;
    private String from = "lawaltoheeb36@gmail.com";
}
