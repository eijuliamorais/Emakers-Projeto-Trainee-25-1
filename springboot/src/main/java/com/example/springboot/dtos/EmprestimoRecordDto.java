package com.example.springboot.dtos;

import java.util.Date;

import io.micrometer.common.lang.NonNull;

public record EmprestimoRecordDto(
    @NonNull Long idLivro,
    @NonNull Long idPessoa,
    Date dataEmprestimo,
    Date dataDevolucao,
    boolean ativo
) {
    
}