package com.example.springboot.repositories;

import com.example.springboot.models.PessoaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import org.springframework.stereotype.Repository;


@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, UUID> {
    
}
