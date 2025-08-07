package com.example.springboot.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "livros")

public class LivroModel implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long idLivro;
    private String titulo;
    private String autor;
    private LocalDate dataPublicacao;
    private String editora;
    private String genero;

    @OneToMany (mappedBy = "livro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmprestimoModel> emprestimos;


}
