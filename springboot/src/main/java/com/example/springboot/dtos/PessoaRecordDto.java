package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public record PessoaRecordDto(
    @NotBlank String nome,
    @NotBlank @Email String email,
    String telefone,
    @NotBlank String cpf,
    String cep
) {
    
}
