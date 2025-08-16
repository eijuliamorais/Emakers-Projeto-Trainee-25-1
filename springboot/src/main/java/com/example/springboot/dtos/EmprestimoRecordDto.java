package com.example.springboot.dtos;

import java.time.LocalDate;

import com.example.springboot.models.enums.EmprestimoStatus;

import io.micrometer.common.lang.NonNull;

public record EmprestimoRecordDto(
    @NonNull Long idLivro,
    Long idPessoa,
    LocalDate dataEmprestimo,
    LocalDate dataDevolucao,
    LocalDate previsaoDevolucao,
    EmprestimoStatus status
) {
    
}