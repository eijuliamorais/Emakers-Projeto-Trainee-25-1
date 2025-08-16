package com.example.springboot.exception;

public class EmailJaCadastradoException extends RuntimeException {
    public EmailJaCadastradoException(String msg) {
        super(msg);
    }
}