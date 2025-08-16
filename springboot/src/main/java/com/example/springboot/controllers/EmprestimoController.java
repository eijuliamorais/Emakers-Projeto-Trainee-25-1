package com.example.springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dtos.EmprestimoRecordDto;
import com.example.springboot.dtos.EmprestimoResponseDto;
import com.example.springboot.dtos.LivroResponseDto;
import com.example.springboot.models.PessoaModel;
import com.example.springboot.models.enums.EmprestimoStatus;
import com.example.springboot.services.EmprestimoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController

public class EmprestimoController {

    @Autowired
    EmprestimoService emprestimoService;

    @Operation(
        summary = "Cadastrar um novo empréstimo",
        description="Registra no sistema um novo empréstimo de livro para uma pessoa, incluindo informações sobre o livro, a pessoa e a data de devolução prevista."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode= "201",
            description= "Empréstimo criado com sucesso.",
            content= @Content(schema= @Schema(implementation = LivroResponseDto.class))
        ),
        @ApiResponse(
            responseCode= "400",
            description= "Os dados enviados são inválidos ou incompletos."
        )
    })
    @PostMapping("/emprestimo")
    public ResponseEntity<EmprestimoResponseDto> criarEmprestimo(
        @RequestBody EmprestimoRecordDto dto, 
        @AuthenticationPrincipal PessoaModel usuarioLogado) {
    
        EmprestimoResponseDto emprestimo = emprestimoService.criarEmprestimo(dto, usuarioLogado);
        return ResponseEntity.ok(emprestimo);
    }

    @Operation(
        summary= "Registrar devolução de um livro",
        description= "Atualiza o status de um empréstimo existente para 'Devolvido'."
    )
    @ApiResponses({
        @ApiResponse(
        responseCode="200",
        description="Devolução registrada com sucesso.",
        content = @Content(schema = @Schema(implementation = EmprestimoResponseDto.class))
        ),
        @ApiResponse(
            responseCode="400",
            description="Não foi encontrado um empréstimo com o ID informado."
        )
    })
    @PutMapping("/emprestimo/devolver/{id}")
    public ResponseEntity<EmprestimoResponseDto> devolver(@PathVariable Long id) {
        var response = emprestimoService.marcarComoDevolvido(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary= "Listar todos os empréstimos",
        description= "Retorna todos os empréstimos cadastrados, incluindo ativos, devolvidos e atrasados."
    )
    
    @ApiResponse(
        responseCode= "200",
        description= "Lista de empréstimos obtida com sucesso."
    )
    @GetMapping("/emprestimo")
    public ResponseEntity<List<EmprestimoResponseDto>> listarTodos() {
        return ResponseEntity.ok(emprestimoService.listarTodos());
    }

    @Operation(
        summary= "Listar apenas empréstimos ativos",
        description= "Retorna todos os empréstimos que ainda não foram devolvidos."
    )
    
    @ApiResponse(
        responseCode= "200",
        description= "Lista de empréstimos ativos obtida com sucesso."
    )
    @GetMapping("/emprestimo/emprestados")
    public ResponseEntity<List<EmprestimoResponseDto>> listarEmprestados() {
        return ResponseEntity.ok(emprestimoService.listarPorStatus(EmprestimoStatus.EMPRESTADO));
    }

    @Operation(
        summary= "Listar apenas devoluções",
        description= "Retorna todos os empréstimos que já foram finalizados com a devolução do livro."
    )
    
    @ApiResponse(
        responseCode= "200",
        description= "Lista de devoluções obtida com sucesso."
    )
    @GetMapping("/emprestimo/devolvidos")
    public ResponseEntity<List<EmprestimoResponseDto>> listarDevolvidos() {
        return ResponseEntity.ok(emprestimoService.listarPorStatus(EmprestimoStatus.DEVOLVIDO));
    }

    @Operation(
        summary= "Listar empréstimos atrasados",
        description= "Retorna todos os empréstimos cuja data de devolução já passou e que ainda não foram devolvidos."
    )
    
    @ApiResponse(
        responseCode= "200",
        description= "Lista de empréstimos atrasados obtida com sucesso."
    )
    @GetMapping("/emprestimo/atrasados")
    public ResponseEntity<List<EmprestimoResponseDto>> listarAtrasados() {
        return ResponseEntity.ok(emprestimoService.listarPorStatus(EmprestimoStatus.ATRASADO));
    }

    @Operation(
        summary= "Buscar empréstimos por ID",
        description= "Retorna os detalhes completos de um empréstimo específico pelo seu ID."
    )
    @ApiResponses({
        @ApiResponse(
        responseCode="200",
        description="Empréstimo encontrado com sucesso",
        content = @Content(schema = @Schema(implementation = LivroResponseDto.class))
        ),
        @ApiResponse(
            responseCode="404",
            description="Não existe um empréstimo com o ID informado."
        )
    })
    @GetMapping("/emprestimo/{id}")
    public ResponseEntity<EmprestimoResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.buscarPorId(id));
    }


}
