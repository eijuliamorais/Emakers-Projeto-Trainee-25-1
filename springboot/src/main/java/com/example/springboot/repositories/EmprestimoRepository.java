package com.example.springboot.repositories;

import com.example.springboot.models.EmprestimoModel;
import com.example.springboot.models.LivroModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<EmprestimoModel, Long> {

    boolean existsByLivroAndAtivo(LivroModel livro, boolean b);

    
}