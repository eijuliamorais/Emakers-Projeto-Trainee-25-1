package com.example.springboot.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.models.PessoaModel;
import com.example.springboot.repositories.PessoaRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.springboot.dtos.PessoaRecordDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
public class PessoaController {

    @Autowired
    PessoaRepository pessoaRepository;

    // Definição de endpoints e métodos para manipulação de pessoas
    @PostMapping("/pessoas")
    public ResponseEntity<PessoaModel> savePessoa(@RequestBody @Valid PessoaRecordDto pessoaRecordDto) {
        var pessoaModel = new PessoaModel();
        BeanUtils.copyProperties(pessoaRecordDto, pessoaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaRepository.save(pessoaModel));
    }
    
    @GetMapping("/pessoas")
    public ResponseEntity<List<PessoaModel>> getallPessoa() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.findAll());
    }

    @GetMapping("/pessoas/{id}")
    public ResponseEntity<Object> getPessoaById(@PathVariable (value = "id") UUID id) {
        Optional<PessoaModel> pessoaModelOptional = pessoaRepository.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pessoaModelOptional.get());
    }
    
    @PutMapping("/pessoas/{id}")
    public ResponseEntity<Object> updatePessoa(@PathVariable (value = "id")  UUID id, @RequestBody @Valid PessoaRecordDto pessoaRecordDto) {
        Optional<PessoaModel> pessoaModelOptional = pessoaRepository.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
        }
        var pessoaModel = pessoaModelOptional.get();
        BeanUtils.copyProperties(pessoaRecordDto, pessoaModel);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.save(pessoaModel));
    }
    
    @DeleteMapping("/pessoas/{id}")
    public ResponseEntity<Object> deletePessoa(@PathVariable (value = "id") UUID id){
        Optional<PessoaModel> pessoaModelOptional = pessoaRepository.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
        }
        pessoaRepository.delete(pessoaModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Pessoa deletada com sucesso");    
    }

}