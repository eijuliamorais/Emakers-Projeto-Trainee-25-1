package com.example.springboot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.io.Serializable;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "emprestimos")
public class EmprestimoModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idEmprestimo;

    private Date dataEmprestimo;
    private Date dataDevolucao;
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "id_pessoa") 
    private PessoaModel pessoa;

    @ManyToOne
    @JoinColumn(name = "id_livro")
    private LivroModel livro;

}
