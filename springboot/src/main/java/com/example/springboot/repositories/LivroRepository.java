package com.example.springboot.repositories;

import com.example.springboot.models.LivroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<LivroModel, Long> {
 
}
