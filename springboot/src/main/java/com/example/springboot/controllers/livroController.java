package com.example.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.example.springboot.models.LivroModel;
import com.example.springboot.services.LivroService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.springboot.dtos.LivroRecordDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
public class livroController {

    @Autowired
    LivroService livroService;//

    @PostMapping("/livros") // Método para salvar um livro
    public ResponseEntity<LivroModel> saveLivro(@RequestBody @Valid LivroRecordDto livroRecordDto) {
        var livroModel = livroService.saveLivro(livroRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroModel);
    }

    
    @GetMapping("/livros") // Método para listar todos os livros
    public ResponseEntity<List<LivroModel>> getallLivro() {
        List<LivroModel> livros = livroService.listaTodos();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/livros/{id}") // Método para listar um livro por ID
    public ResponseEntity<Object> getPessoaById(@PathVariable (value = "id") long id) {
        try {
            LivroModel livro = livroService.getLivroById(id);
            return ResponseEntity.ok(livro);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @PutMapping("/livros/{id}") // Método para atualizar um livro
    public ResponseEntity<Object> updatePessoa(@PathVariable (value = "id")  long id, @RequestBody @Valid LivroRecordDto livroRecordDto) {
        try {
            LivroModel livro = livroService.updateLivro(id, livroRecordDto);
            return ResponseEntity.ok(livro);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @DeleteMapping("livros/{id}") // Método para deletar um livro
    public ResponseEntity<Object> deleteLivro(@PathVariable(value = "id") long id) {
        try {
            livroService.deleteLivro(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }    
    }

}