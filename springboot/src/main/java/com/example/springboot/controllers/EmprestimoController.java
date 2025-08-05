package com.example.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.example.springboot.services.EmprestimoService;
import com.example.springboot.dtos.EmprestimoRecordDto;
import com.example.springboot.models.EmprestimoModel;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;



@RestController
public class EmprestimoController {

    @Autowired
    EmprestimoService emprestimoService;

    //Método para salvar um empréstimo
    @PostMapping("/emprestimos")
    public ResponseEntity<EmprestimoModel> registrarEmprestimo(@RequestBody EmprestimoRecordDto emprestimoRecordDto) {
        EmprestimoModel emprestimoModel = emprestimoService.realizarEmprestimo(emprestimoRecordDto.idPessoa(), emprestimoRecordDto.idLivro());
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimoModel);
    }
    
    
    // Metodo para devolver um livro
    @PutMapping("/emprestimos/devolucao/{id}")
    public ResponseEntity<EmprestimoModel> devolverLivro(@PathVariable Long id) {
        try {
            EmprestimoModel emprestimoModel = emprestimoService.devolverLivro(id);
            return ResponseEntity.ok(emprestimoModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Método para listar todos os empréstimos
    @GetMapping("/emprestimos")
    public ResponseEntity<List<EmprestimoModel>> getAllEmprestimos() {
        List<EmprestimoModel> emprestimos = emprestimoService.listarEmprestimos();
        return ResponseEntity.ok(emprestimos);
    }

    // Método para buscar um empréstimo por ID
    @GetMapping("/emprestimos/{id}")    
    public ResponseEntity<Object> buscarEmprestimoPorId(@PathVariable Long id) {
        try {
            EmprestimoModel emprestimo = emprestimoService.buscarEmprestimoPorId(id);
            return ResponseEntity.ok(emprestimo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Método para listar empréstimos ativos
    @GetMapping("/emprestimos/ativos")
    public ResponseEntity<List<EmprestimoModel>> buscarEmprestimosAtivos() {
        List<EmprestimoModel> emprestimosAtivos = emprestimoService.listarEmprestimosAtivos();
        return ResponseEntity.ok(emprestimosAtivos);
    }

    // Método para listar empréstimos por pessoa
    @GetMapping("/emprestimos/pessoa/{idPessoa}")
    public ResponseEntity<List<EmprestimoModel>> buscarEmprestimosPorPessoa(@PathVariable Long idPessoa) {
        List<EmprestimoModel> emprestimos = emprestimoService.listarEmprestimosPorPessoa(idPessoa);
        return ResponseEntity.ok(emprestimos);
    }

    // Método para listar empréstimos por livro
    @GetMapping("/emprestimos/livro/{idLivro}")
    public ResponseEntity<List<EmprestimoModel>> buscarEmprestimosPorLivro(@PathVariable Long idLivro) {
        List<EmprestimoModel> emprestimos = emprestimoService.listarEmprestimosPorLivro(idLivro);
        return ResponseEntity.ok(emprestimos);
    }

    // Método para verificar se um livro está emprestado
    @GetMapping("/emprestimos/livro/emprestado/{idLivro}")
    public ResponseEntity<Boolean> LivroEmprestado(@PathVariable Long idLivro) {
        boolean isEmprestado = emprestimoService.LivroEmprestado(idLivro);
        return ResponseEntity.ok(isEmprestado);
    }


}
