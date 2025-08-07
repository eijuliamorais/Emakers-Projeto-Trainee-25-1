//dto para resposta sem o emprestimo
package com.example.springboot.dtos;

import com.example.springboot.models.enums.PessoaCargo;

public record PessoaResponseDto(
    Long id,
    String nome,
    String email,
    String telefone,
    String cep,
    String cpf,
    PessoaCargo cargo
) {
    
    public PessoaResponseDto(Long id, String nome, String email, String telefone, String cep, String cpf, PessoaCargo cargo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cep = cep;
        this.cpf = cpf;
        this.cargo = cargo;
    }
}

