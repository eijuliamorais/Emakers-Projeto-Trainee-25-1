package com.example.springboot.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.models.LivroModel;
import com.example.springboot.repositories.LivroRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.springboot.dtos.LivroRecordDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
public class livroController {

    @Autowired
    LivroRepository livroRepository;

    @PostMapping("/livros")
    public ResponseEntity<LivroModel> saveLivro(@RequestBody @Valid LivroRecordDto livroRecordDto) {
        var pessoaModel = new LivroModel();
        BeanUtils.copyProperties(livroRecordDto, pessoaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroRepository.save(pessoaModel));
    }
    
    @GetMapping("/livros")
    public ResponseEntity<List<LivroModel>> getallLivro() {
        return ResponseEntity.status(HttpStatus.OK).body(livroRepository.findAll());
    }

    @GetMapping("/livros/{id}")
    public ResponseEntity<Object> getPessoaById(@PathVariable (value = "id") UUID id) {
        Optional<LivroModel> livroModelOptional = livroRepository.findById(id);
        if (!livroModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(livroModelOptional.get());
    }
    
    @PutMapping("/livros/{id}")
    public ResponseEntity<Object> updatePessoa(@PathVariable (value = "id")  UUID id, @RequestBody @Valid LivroRecordDto livroRecordDto) {
        Optional<LivroModel> livroModelOptional = livroRepository.findById(id);
        if (!livroModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrada");
        }
        var livroModel = livroModelOptional.get();
        BeanUtils.copyProperties(livroRecordDto, livroModel);
        return ResponseEntity.status(HttpStatus.OK).body(livroRepository.save(livroModel));
    }
    
    @DeleteMapping(" livros/{id}")
    public ResponseEntity<Object> deletePessoa(@PathVariable (value = "id") UUID id){
        Optional<LivroModel> livroModelOptional = livroRepository.findById(id);
        if (!livroModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrada");
        }
        livroRepository.delete(livroModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Livro deletada com sucesso");    
    }

}