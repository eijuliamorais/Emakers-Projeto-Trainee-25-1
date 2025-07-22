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
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "livros")

public class LivroModel implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private UUID idLivro;
    private String titulo;
    private String autor;
    private Date dataPublicacao;
    private String editora;
    private String genero;

}
