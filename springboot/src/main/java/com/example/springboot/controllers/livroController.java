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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;


@RestController
public class livroController {

    @Autowired
    LivroService livroService;//,

    @Operation(
        summary = "Cadastrar um novo livro",
        description="Registra um novo livro no sistema, informando título, autor e demais informações necessárias."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode= "201",
            description= "Livro criado com sucesso.",
            content= @Content(schema= @Schema(implementation = LivroResponseDto.class))
        ),
        @ApiResponse(responseCode= "400", description= "Os dados enviados são inválidos ou incompletos."),
        @ApiResponse(responseCode = "403", description = "Não autorizado - Usuário sem permissão")
    })
    @PostMapping("/livros") // Método para salvar um livro
    public ResponseEntity<LivroResponseDto> saveLivro(@RequestBody @Valid LivroRecordDto livroRecordDto) {
        LivroResponseDto livro = livroService.saveLivro(livroRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(livro);
    }

    @Operation(
        summary= "Listar todos os livros",
        description= "Retorna todos os livros cadastrados no sistema, com seus respectivos detalhes."
    )
    
    @ApiResponse(
        responseCode= "200",
        description= "Lista de livros obtida com sucesso."
    )

    @GetMapping("/livros") // Método para listar todos os livros
    public ResponseEntity<List<LivroResponseDto>> getallLivro() {
        List<LivroResponseDto> livros = livroService.listaTodos();
        return ResponseEntity.ok(livros);
    }

    @Operation(
        summary= "Buscar livros por ID",
        description= "Retornar dados do livro buscado."
    )
    @ApiResponses({
        @ApiResponse(
        responseCode="200",
        description="Livro encontrado com sucesso",
        content = @Content(schema = @Schema(implementation = LivroResponseDto.class))
        ),
        @ApiResponse(
            responseCode="400",
            description="Nenhum livro foi localizado com o ID informado."
        )
    })
    @GetMapping("/livros/{id}") // Método para listar um livro por ID
    public ResponseEntity<Object> getPessoaById(@PathVariable (value = "id") long id) {
            LivroResponseDto livro = livroService.getLivroById(id);
            return ResponseEntity.ok(livro);
    }
    
    @Operation(
        summary= "Atualizar um livro",
        description= "Atualizar dados de um livro existente."
    )
    @ApiResponses({
        @ApiResponse(
        responseCode="200",
        description="Livro atualizado com sucesso",
        content = @Content(schema = @Schema(implementation = LivroResponseDto.class))
        ),
        @ApiResponse(responseCode="404", description="Nenhum livro foi localizado com o ID informado."),
        @ApiResponse(responseCode = "403", description = "Não autorizado - Usuário sem permissão")
    })
    @PutMapping("/livros/{id}") // Método para atualizar um livro
    public ResponseEntity<Object> updateLivro(@PathVariable (value = "id")  long id, @RequestBody @Valid LivroRecordDto livroRecordDto) {
            LivroResponseDto livro = livroService.updateLivro(id, livroRecordDto);
            return ResponseEntity.ok(livro);
    }
    
    @Operation(
        summary= "Deletar um livro",
        description= "Deletar um livro do sistema pelo ID."
    )
    @ApiResponses({
        @ApiResponse(
        responseCode="204",
        description="Livro deletado com sucesso",
        content = @Content(schema = @Schema(implementation = LivroResponseDto.class))
        ),
        @ApiResponse(responseCode="404", description="Nenhum livro foi localizado com o ID informado."),
        @ApiResponse(responseCode = "403", description = "Não autorizado - Usuário sem permissão")
    })
    @DeleteMapping("livros/{id}") // Método para deletar um livro
    public ResponseEntity<Object> deleteLivro(@PathVariable(value = "id") long id) {
            livroService.deleteLivro(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}