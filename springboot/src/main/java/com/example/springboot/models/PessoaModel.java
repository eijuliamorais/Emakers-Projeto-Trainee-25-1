package com.example.springboot.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.springboot.models.enums.PessoaCargo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name = "Pessoa")
public class PessoaModel implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idPessoa;
    private String nome;
    @Column(unique = true)
    private String email;
    private String senha;
    private String telefone;
    private String cep;
    private String cpf;
    private PessoaCargo cargo;

    public PessoaModel() {
    }

    public PessoaModel(String email, String senha, PessoaCargo cargo) {

        this.email = email;
        this.senha = senha;
        this.cargo = cargo;
        
    }

    @OneToMany  (mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmprestimoModel> emprestimos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       if(this.cargo == PessoaCargo.ADMIN) 
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else 
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
       
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }
}




    