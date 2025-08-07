package com.example.springboot.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot.dtos.PessoaRecordDto;
import com.example.springboot.dtos.PessoaResponseDto;
import com.example.springboot.models.PessoaModel;
import com.example.springboot.repositories.PessoaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PessoaService {
    
    @Autowired
    private PessoaRepository pessoaRepository;

    // Método para salvar um pessoa POST
    public PessoaResponseDto savePessoa (PessoaRecordDto pessoaRecordDto) {
        var pessoaModel = new PessoaModel();
        BeanUtils.copyProperties(pessoaRecordDto, pessoaModel);
        PessoaModel savedPessoa = pessoaRepository.save(pessoaModel);
        return new PessoaResponseDto(
            savedPessoa.getIdPessoa(),
            savedPessoa.getNome(),
            savedPessoa.getEmail(),
            savedPessoa.getTelefone(),
            savedPessoa.getCep(),
            savedPessoa.getCpf(),
            savedPessoa.getCargo()
        );
    }


    // Método para listar todos os pessoa GET
    public List<PessoaResponseDto> listaTodos() {
        return pessoaRepository.findAll().stream()
            .map(pessoa -> new PessoaResponseDto(
                pessoa.getIdPessoa(),
                pessoa.getNome(),
                pessoa.getEmail(),
                pessoa.getTelefone(),
                pessoa.getCep(),
                pessoa.getCpf(),
                pessoa.getCargo()
            ))
            .collect(Collectors.toList());
    }

    // Método para listar um pessoa GET
    public PessoaResponseDto getPessoaById(long id) {
        Optional<PessoaModel> pessoaModelOptional = pessoaRepository.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            throw new EntityNotFoundException("Pessoa com id " + id + " não encontrada");
        }
        return new PessoaResponseDto(
            pessoaModelOptional.get().getIdPessoa(),
            pessoaModelOptional.get().getNome(),
            pessoaModelOptional.get().getEmail(),
            pessoaModelOptional.get().getTelefone(),
            pessoaModelOptional.get().getCep(),
            pessoaModelOptional.get().getCpf(),
            pessoaModelOptional.get().getCargo()
        );
    }

    // Método para atualizar um pessoa PUT
    public PessoaResponseDto updatePessoa(long id, PessoaRecordDto pessoaRecordDto) {
        Optional<PessoaModel> pessoaModelOptional = pessoaRepository.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            throw new EntityNotFoundException("Pessoa com id " + id + " não encontrada");
        }
        PessoaModel pessoaModel = pessoaModelOptional.get();
        BeanUtils.copyProperties(pessoaRecordDto, pessoaModel);
        PessoaModel uptadPessoaModel = pessoaRepository.save(pessoaModel);

        return new PessoaResponseDto(
            uptadPessoaModel.getIdPessoa(),
            uptadPessoaModel.getNome(),
            uptadPessoaModel.getEmail(),
            uptadPessoaModel.getTelefone(),
            uptadPessoaModel.getCep(),
            uptadPessoaModel.getCpf(),
            uptadPessoaModel.getCargo()
        );
    }

    // Método para deletar um pessoa DELETE
    public void deletePessoa(long id) {
        Optional<PessoaModel> pessoaModelOptional = pessoaRepository.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            throw new EntityNotFoundException("Pessoa com id " + id + " não encontrada");
        }
        pessoaRepository.delete(pessoaModelOptional.get());
    }

    

}