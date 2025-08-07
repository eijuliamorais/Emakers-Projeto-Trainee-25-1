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

import com.example.springboot.dtos.PessoaRecordDto;
import com.example.springboot.dtos.PessoaResponseDto;
import com.example.springboot.services.PessoaService;

import jakarta.validation.Valid;


@RestController
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    // Definição de endpoints e métodos para manipulação de pessoas
    @PostMapping("/pessoas")
    public ResponseEntity<PessoaResponseDto> savePessoa(@RequestBody @Valid PessoaRecordDto pessoaRecordDto) {
       PessoaResponseDto pessoa = pessoaService.savePessoa(pessoaRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
    }
    
    @GetMapping("/pessoas")
    public ResponseEntity<List<PessoaResponseDto>> getallPessoa() {
        List<PessoaResponseDto> pessoas = pessoaService.listaTodos();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/pessoas/{id}")
    public ResponseEntity<Object> getPessoaById(@PathVariable (value = "id") long id) {
       try {
            PessoaResponseDto pessoa = pessoaService.getPessoaById(id);
            return ResponseEntity.ok(pessoa);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @PutMapping("/pessoas/{id}")
    public ResponseEntity<Object> updatePessoa(@PathVariable (value = "id")  long id, @RequestBody @Valid PessoaRecordDto pessoaRecordDto) {
        try {
            PessoaResponseDto pessoa = pessoaService.updatePessoa(id, pessoaRecordDto);

            return ResponseEntity.ok(pessoa);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/pessoas/{id}")
    public ResponseEntity<Object> deletePessoa(@PathVariable (value = "id") long id){
        try {
            pessoaService.deletePessoa(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } 
    }

}