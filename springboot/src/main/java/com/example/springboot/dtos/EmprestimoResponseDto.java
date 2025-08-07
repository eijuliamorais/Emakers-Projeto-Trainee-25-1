package com.example.springboot.dtos;

import java.time.LocalDate;

import com.example.springboot.models.enums.EmprestimoStatus;

// DTO para resposta de empréstimo, com nome de pessoa e título do livro
public record EmprestimoResponseDto(
    Long id,
    String nomePessoa,
    String tituloLivro,
    LocalDate dataEmprestimo,
    LocalDate dataDevolucao,
    LocalDate previsaoDevolucao,
    EmprestimoStatus status
) {
}