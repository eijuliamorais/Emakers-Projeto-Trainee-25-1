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
        return new PessoaResponseDto(
            savedPessoa.getIdPessoa(),
            savedPessoa.getNome(),
            savedPessoa.getEmail(),
            savedPessoa.getTelefone(),
            savedPessoa.getCep(),
            savedPessoa.getCpf(),
            savedPessoa.getCargo(),
            savedPessoa.getLogradouro(),
            savedPessoa.getComplemento(),
            savedPessoa.getBairro(),
            savedPessoa.getLocalidade(),
            savedPessoa.getUf()
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
                pessoa.getCargo(),
                pessoa.getLogradouro(),
                pessoa.getComplemento(),
                pessoa.getBairro(),
                pessoa.getLocalidade(),
                pessoa.getUf()
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
            pessoaModelOptional.get().getCargo(),
            pessoaModelOptional.get().getLogradouro(),
            pessoaModelOptional.get().getComplemento(),
            pessoaModelOptional.get().getBairro(),
            pessoaModelOptional.get().getLocalidade(),
            pessoaModelOptional.get().getUf()

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

        var endereco = viaCepService.buscaCepResponseDto(pessoaRecordDto.cep());
        if (endereco != null) {
            pessoaModel.setLogradouro(endereco.logradouro());
            pessoaModel.setComplemento(endereco.complemento());
            pessoaModel.setBairro(endereco.bairro());
            pessoaModel.setLocalidade(endereco.localidade());
            pessoaModel.setUf(endereco.uf());
        }

        PessoaModel uptadPessoaModel = pessoaRepository.save(pessoaModel);

        return new PessoaResponseDto(
            uptadPessoaModel.getIdPessoa(),
            uptadPessoaModel.getNome(),
            uptadPessoaModel.getEmail(),
            uptadPessoaModel.getTelefone(),
            uptadPessoaModel.getCep(),
            uptadPessoaModel.getCpf(),
            uptadPessoaModel.getCargo(),
            uptadPessoaModel.getLogradouro(),
            uptadPessoaModel.getComplemento(),
            uptadPessoaModel.getBairro(),
            uptadPessoaModel.getLocalidade(),
            uptadPessoaModel.getUf()
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