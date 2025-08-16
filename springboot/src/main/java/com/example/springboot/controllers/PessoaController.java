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

import com.example.springboot.dtos.LivroResponseDto;
import com.example.springboot.dtos.PessoaRecordDto;
import com.example.springboot.dtos.PessoaResponseDto;
import com.example.springboot.services.PessoaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;


@RestController
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @Operation(
        summary = "Cadastrar uma nova Pessoa",
        description="Crie uma nova Pessoa no sistema informando nome, email, telefone outros dados."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode= "201",
            description= "Pessoa criado com sucesso",
            content= @Content(schema= @Schema(implementation = PessoaResponseDto.class))
        ),
        @ApiResponse(responseCode= "400", description= "Dados inválidos para criação"),
        @ApiResponse(responseCode = "403", description = "Não autorizado - Usuário sem permissão")
    })
    @PostMapping("/pessoas")
    public ResponseEntity<PessoaResponseDto> savePessoa(@RequestBody @Valid PessoaRecordDto pessoaRecordDto) {
       PessoaResponseDto pessoa = pessoaService.savePessoa(pessoaRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
    }
    
    @Operation(
        summary= "Listar todas as pessoas",
        description= "Retorna a lista de todas as pessoas cadastradas."
    )
    @ApiResponses({
    @ApiResponse(responseCode= "200", description= "Lista retornada com sucesso"),
    @ApiResponse(responseCode = "403", description = "Não autorizado - Usuário sem permissão")
    })
    @GetMapping("/pessoas")
    public ResponseEntity<List<PessoaResponseDto>> getallPessoa() {
        List<PessoaResponseDto> pessoas = pessoaService.listaTodos();
        return ResponseEntity.ok(pessoas);
    }

    @Operation(
        summary= "Buscar pessoas por ID",
        description= "Retornar dados da pessoa buscada."
    )
    @ApiResponses({
        @ApiResponse(
        responseCode="200",
        description="Pessoa encontrada com sucesso",
        content = @Content(schema = @Schema(implementation = LivroResponseDto.class))
        ),
        @ApiResponse(responseCode="404", description="Pessoa não encontrada"),
        @ApiResponse(responseCode = "403", description = "Não autorizado - Usuário sem permissão")
    })
    @GetMapping("/pessoas/{id}")
    public ResponseEntity<Object> getPessoaById(@PathVariable (value = "id") long id) {
            PessoaResponseDto pessoa = pessoaService.getPessoaById(id);
            return ResponseEntity.ok(pessoa);
    }
    
        @Operation(
        summary= "Atualizar uma pessoa",
        description= "Atualizar dados de uma pessoa existente."
    )
    @ApiResponses({
        @ApiResponse(
        responseCode="200",
        description="Dados de pessoa atualizados com sucesso",
        content = @Content(schema = @Schema(implementation = PessoaResponseDto.class))
        ),
        @ApiResponse(responseCode="404", description="Pessoa não encontrada"),
        @ApiResponse(responseCode = "403", description = "Não autorizado - Usuário sem permissão")
    })
    @PutMapping("/pessoas/{id}")
    public ResponseEntity<Object> updatePessoa(@PathVariable (value = "id")  long id, @RequestBody @Valid PessoaRecordDto pessoaRecordDto) {
            PessoaResponseDto pessoa = pessoaService.updatePessoa(id, pessoaRecordDto);
            return ResponseEntity.ok(pessoa);
    }
    
    @Operation(
        summary= "Deletar uma pessoa",
        description= "Deletar uma pessoa do sistema pelo ID."
    )
    @ApiResponses({
        @ApiResponse(
        responseCode="204",
        description="Pessoa deletada com sucesso",
        content = @Content(schema = @Schema(implementation = PessoaResponseDto.class))
        ),
        @ApiResponse(responseCode="404", description="Pessoa não encontrada"),
        @ApiResponse(responseCode = "403", description = "Não autorizado - Usuário sem permissão")
    })
    @DeleteMapping("/pessoas/{id}")
    public ResponseEntity<Object> deletePessoa(@PathVariable (value = "id") long id){
            pessoaService.deletePessoa(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}