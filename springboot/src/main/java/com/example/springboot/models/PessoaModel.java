package com.example.springboot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "Pessoa")
public class PessoaModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idPessoa;
    private String nome;
    private String email;
    private String telefone;
    private String cep;
    private String cpf;
}




    