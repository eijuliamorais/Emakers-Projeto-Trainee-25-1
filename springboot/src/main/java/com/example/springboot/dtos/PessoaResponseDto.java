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
    PessoaCargo cargo,
    String logradouro, 
    String complemento,
    String bairro,
    String localidade, 
    String uf 
) {}
    


