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

     @Autowired
    private ViaCepService viaCepService;

    // Método para salvar um pessoa POST
    public PessoaResponseDto savePessoa (PessoaRecordDto pessoaRecordDto) {
        var pessoaModel = new PessoaModel();
        BeanUtils.copyProperties(pessoaRecordDto, pessoaModel);

        var endereco = viaCepService.buscaCepResponseDto(pessoaRecordDto.cep());
        if (endereco != null) {
            pessoaModel.setLogradouro(endereco.logradouro());
            pessoaModel.setComplemento(endereco.complemento());
            pessoaModel.setBairro(endereco.bairro());
            pessoaModel.setLocalidade(endereco.localidade());
            pessoaModel.setUf(endereco.uf());
        }

        PessoaModel savedPessoa = pessoaRepository.save(pessoaModel);
        return  converterParaResponseDto(savedPessoa);
    }


    // Método para listar todos os pessoa GET
    public List<PessoaResponseDto> listaTodos() {
        return pessoaRepository.findAll().stream()
            .map(this::converterParaResponseDto)
            .collect(Collectors.toList());
    }

    // Método para listar um pessoa GET
    public PessoaResponseDto getPessoaById(long id) {
        Optional<PessoaModel> pessoaModelOptional = pessoaRepository.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            throw new EntityNotFoundException("Pessoa com id " + id + " não encontrada");
        }
        return converterParaResponseDto(pessoaModelOptional.get());
    }

    // Método para atualizar um pessoa PUT
    public PessoaResponseDto updatePessoa(long id, PessoaRecordDto pessoaRecordDto) {
        Optional<PessoaModel> pessoaModelOptional = pessoaRepository.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            throw new EntityNotFoundException("Pessoa com id " + id + " não encontrada");
        }
        PessoaModel pessoaModel = pessoaModelOptional.get();
        BeanUtils.copyProperties(pessoaRecordDto, pessoaModel);

        var endereco = viaCepService.buscaCepResponseDto(pessoaRecordDto.cep());
        if (endereco != null) {
            pessoaModel.setLogradouro(endereco.logradouro());
            pessoaModel.setComplemento(endereco.complemento());
            pessoaModel.setBairro(endereco.bairro());
            pessoaModel.setLocalidade(endereco.localidade());
            pessoaModel.setUf(endereco.uf());
        }

        PessoaModel uptadPessoaModel = pessoaRepository.save(pessoaModel);

        return converterParaResponseDto(uptadPessoaModel);
    }

    // Método para deletar um pessoa DELETE
    public void deletePessoa(long id) {
        Optional<PessoaModel> pessoaModelOptional = pessoaRepository.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            throw new EntityNotFoundException("Pessoa com id " + id + " não encontrada");
        }
        pessoaRepository.delete(pessoaModelOptional.get());
    }

    private PessoaResponseDto converterParaResponseDto(PessoaModel p){
        return new PessoaResponseDto(
            p.getIdPessoa(),
            p.getNome(),
            p.getEmail(),
            p.getTelefone(),
            p.getCep(),
            p.getCpf(),
            p.getCargo(),
            p.getLogradouro(),
            p.getComplemento(),
            p.getBairro(),
            p.getLocalidade(),
            p.getUf()
        );
    }
    

}