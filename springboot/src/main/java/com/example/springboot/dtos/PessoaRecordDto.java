package com.example.springboot.dtos;

import com.example.springboot.models.enums.PessoaCargo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PessoaRecordDto(
    @NotBlank String nome,
    @NotBlank @Email String email,
    String telefone,
    @NotBlank String cpf,
    String cep,
    PessoaCargo cargo,
    @NotBlank String senha

) {
    
} 
