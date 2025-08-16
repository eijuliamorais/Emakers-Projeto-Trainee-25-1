package com.example.springboot.exception;

public class ItemNaoEncontradoException extends RuntimeException {
    public ItemNaoEncontradoException(String msg){
        super(msg);
    }
    
}
