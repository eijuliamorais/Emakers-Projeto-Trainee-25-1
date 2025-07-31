package com.example.springboot.repositories;

import com.example.springboot.models.EmprestimoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<EmprestimoModel, Long> {
    

    
}