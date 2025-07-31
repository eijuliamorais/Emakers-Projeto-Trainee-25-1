package com.example.springboot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
    
    @ManyToMany
    @JoinColumn(name = "livro_id")
    private LivroModel livro;

    @ManyToMany
    @JoinColumn(name = "usuario_id")
    private PessoaModel usuario;

    private Date dataEmprestimo;
    private Date dataDevolucao;
    private boolean ativo;

}
