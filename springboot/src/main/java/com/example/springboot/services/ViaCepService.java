package com.example.springboot.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.springboot.dtos.ViaCepResponseDto;

@Service
public class ViaCepService {

    public ViaCepResponseDto buscaCepResponseDto(String cep){
        String url = UriComponentsBuilder
            .fromHttpUrl("https://viacep.com.br/ws/" + cep + "/json/")
            .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, ViaCepResponseDto.class);
    }
}
