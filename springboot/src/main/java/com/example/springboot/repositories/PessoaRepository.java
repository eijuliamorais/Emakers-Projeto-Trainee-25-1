package com.example.springboot.repositories;

import com.example.springboot.models.PessoaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.security.core.userdetails.UserDetails;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, Long> {
    UserDetails findByEmail(String email);
}


