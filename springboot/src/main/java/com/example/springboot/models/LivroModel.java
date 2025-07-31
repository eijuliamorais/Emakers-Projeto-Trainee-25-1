package com.example.springboot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


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
    private Date dataPublicacao;
    private String editora;
    private String genero;

    @OneToMany (mappedBy = "livro", cascade = CascadeType.ALL)
    private List<EmprestimoModel> emprestimos;


}
