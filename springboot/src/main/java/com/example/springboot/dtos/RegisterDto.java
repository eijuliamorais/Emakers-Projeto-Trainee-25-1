package com.example.springboot.dtos;

import com.example.springboot.models.enums.PessoaCargo;

public record RegisterDto (String email, String senha, PessoaCargo cargo) {
    
}
