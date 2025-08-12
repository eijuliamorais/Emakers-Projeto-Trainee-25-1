package com.example.springboot.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record LivroRecordDto(
    @NotBlank String titulo,
    String autor,
    LocalDate dataPublicacao,
    String editora,
    String genero,
    Integer quantidade
) {
    
}
