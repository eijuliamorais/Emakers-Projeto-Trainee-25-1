package com.example.springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dtos.LivroRecordDto;
import com.example.springboot.dtos.LivroResponseDto;
import com.example.springboot.services.LivroService;

import jakarta.validation.Valid;


@RestController
public class livroController {

    @Autowired
    LivroService livroService;//

    @PostMapping("/livros") // Método para salvar um livro
    public ResponseEntity<LivroResponseDto> saveLivro(@RequestBody @Valid LivroRecordDto livroRecordDto) {
        LivroResponseDto livro = livroService.saveLivro(livroRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(livro);
    }

    
    @GetMapping("/livros") // Método para listar todos os livros
    public ResponseEntity<List<LivroResponseDto>> getallLivro() {
        List<LivroResponseDto> livros = livroService.listaTodos();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/livros/{id}") // Método para listar um livro por ID
    public ResponseEntity<Object> getPessoaById(@PathVariable (value = "id") long id) {
        try {
            LivroResponseDto livro = livroService.getLivroById(id);
            return ResponseEntity.ok(livro);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @PutMapping("/livros/{id}") // Método para atualizar um livro
    public ResponseEntity<Object> updateLivro(@PathVariable (value = "id")  long id, @RequestBody @Valid LivroRecordDto livroRecordDto) {
        try {
            LivroResponseDto livro = livroService.updateLivro(id, livroRecordDto);
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