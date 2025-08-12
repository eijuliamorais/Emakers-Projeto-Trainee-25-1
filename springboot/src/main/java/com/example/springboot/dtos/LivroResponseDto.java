package com.example.springboot.dtos;

import java.time.LocalDate;

// DTO para resposta de livro, sem detalhes de empréstimo
public record LivroResponseDto(
    Long idLivro,
    String titulo,
    String autor,
    String editora,
    LocalDate dataPublicacao,
    String genero,
    Integer quantidade
) {
}



