package com.example.springboot.dtos;

public record ViaCepResponseDto(
    String cep,
    String logradouro, 
    String complemento,
    String bairro,
    String localidade, 
    String uf 
) {}

