package com.example.springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dtos.EmprestimoRecordDto;
import com.example.springboot.dtos.EmprestimoResponseDto;
import com.example.springboot.models.enums.EmprestimoStatus;
import com.example.springboot.services.EmprestimoService;



@RestController

public class EmprestimoController {

    @Autowired
    EmprestimoService emprestimoService;

    @PostMapping("/emprestimo")
    public ResponseEntity<EmprestimoResponseDto> criar(@RequestBody EmprestimoRecordDto dto) {
        var response = emprestimoService.criarEmprestimo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/devolver/{id}")
    public ResponseEntity<EmprestimoResponseDto> devolver(@PathVariable Long id) {
        var response = emprestimoService.marcarComoDevolvido(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/emprestimo")
    public ResponseEntity<List<EmprestimoResponseDto>> listarTodos() {
        return ResponseEntity.ok(emprestimoService.listarTodos());
    }

    @GetMapping("/emprestados")
    public ResponseEntity<List<EmprestimoResponseDto>> listarEmprestados() {
        return ResponseEntity.ok(emprestimoService.listarPorStatus(EmprestimoStatus.EMPRESTADO));
    }

    @GetMapping("/devolvidos")
    public ResponseEntity<List<EmprestimoResponseDto>> listarDevolvidos() {
        return ResponseEntity.ok(emprestimoService.listarPorStatus(EmprestimoStatus.DEVOLVIDO));
    }

    @GetMapping("/atrasados")
    public ResponseEntity<List<EmprestimoResponseDto>> listarAtrasados() {
        return ResponseEntity.ok(emprestimoService.listarPorStatus(EmprestimoStatus.ATRASADO));
    }

    @GetMapping("/emprestimo/{id}")
    public ResponseEntity<EmprestimoResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.buscarPorId(id));
    }


}
