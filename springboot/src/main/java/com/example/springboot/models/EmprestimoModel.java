package com.example.springboot.models;

import java.io.Serializable;
import java.time.LocalDate;

import com.example.springboot.models.enums.EmprestimoStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "emprestimos")
public class EmprestimoModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idEmprestimo;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private LocalDate previsaoDevolucao;
    @Enumerated(EnumType.STRING)
    private EmprestimoStatus status;

    @ManyToOne
    @JoinColumn(name = "id_pessoa") 
    private PessoaModel pessoa;

    @ManyToOne
    @JoinColumn(name = "id_livro")
    private LivroModel livro;


}
