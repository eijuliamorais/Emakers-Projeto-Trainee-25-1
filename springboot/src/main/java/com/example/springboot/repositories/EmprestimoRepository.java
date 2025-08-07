package com.example.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.models.EmprestimoModel;
import com.example.springboot.models.LivroModel;
import com.example.springboot.models.enums.EmprestimoStatus;

@Repository
public interface EmprestimoRepository extends JpaRepository<EmprestimoModel, Long> {

    boolean existsByLivroAndStatus(LivroModel livro, EmprestimoStatus status);
    List<EmprestimoModel> findByStatus(EmprestimoStatus status);


    
}