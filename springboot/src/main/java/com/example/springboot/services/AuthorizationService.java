package com.example.springboot.services;

import org.springframework.stereotype.Service;

import com.example.springboot.repositories.PessoaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private PessoaRepository pessoaRepository;

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return pessoaRepository.findByEmail(username);
    }
}
