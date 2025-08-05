package com.example.springboot.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.springboot.repositories.PessoaRepository;
import com.example.springboot.dtos.PessoaRecordDto;
import com.example.springboot.models.PessoaModel;


import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {
    
    @Autowired
    private PessoaRepository pessoaRepository;

    // Método para salvar um pessoa POST
    public PessoaModel savePessoa (PessoaRecordDto pessoaRecordDto) {
        var pessoaModel = new PessoaModel();
        BeanUtils.copyProperties(pessoaRecordDto, pessoaModel);
        return pessoaRepository.save(pessoaModel);
    }


    // Método para listar todos os pessoa GET
    public List<PessoaModel> listaTodos() {
        return pessoaRepository.findAll();
    }

    // Método para listar um pessoa GET
    public PessoaModel getPessoaById(@PathVariable long id) {
        Optional<PessoaModel> pessoaModelOptional = pessoaRepository.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            throw new RuntimeException("Pessoa não encontrada");
        }
        return pessoaModelOptional.get();
    }

    // Método para atualizar um pessoa PUT
    public PessoaModel updatePessoa(long id, PessoaRecordDto pessoaRecordDto) {
        Optional<PessoaModel> pessoaModelOptional = pessoaRepository.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            throw new RuntimeException("Pessoa não encontrada");
        }
        var pessoaModel = pessoaModelOptional.get();
        BeanUtils.copyProperties(pessoaRecordDto, pessoaModel);
        return pessoaRepository.save(pessoaModel);
    }

    // Método para deletar um pessoa DELETE
    public void deletePessoa(long id) {
        Optional<PessoaModel> pessoaModelOptional = pessoaRepository.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            throw new RuntimeException("Pessoa não encontrada");
        }
        pessoaRepository.delete(pessoaModelOptional.get());
    }

    

}