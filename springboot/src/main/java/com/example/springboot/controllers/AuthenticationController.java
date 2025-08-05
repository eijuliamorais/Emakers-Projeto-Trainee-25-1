package com.example.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.config.security.TokenService;
import com.example.springboot.dtos.AuthenticationDto;
import com.example.springboot.dtos.LoginResponseDto;
import com.example.springboot.dtos.RegisterDto;
import com.example.springboot.models.PessoaModel;
import com.example.springboot.repositories.PessoaRepository;

import jakarta.validation.Valid;





@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> emailLogin(@RequestBody @Valid AuthenticationDto data){
        var usernamePassword =new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.gerarToken((PessoaModel) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDto data) {
        if(this.pessoaRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().body("Erro: Email já cadastrado!");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        PessoaModel newUser = new PessoaModel(data.email(), encryptedPassword, data.cargo());

        this.pessoaRepository.save(newUser);
        return ResponseEntity.ok().body("Usuário registrado com sucesso!");
    }


}
