package com.example.springboot.dtos;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;

public record LivroRecordDto(
    @NotBlank String titulo,
    String autor,
    Date dataPublicacao,
    String editora,
    String genero
) {
    
}
