package com.example.springboot.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springboot.dtos.PessoaRecordDto;
import com.example.springboot.dtos.PessoaResponseDto;
import com.example.springboot.dtos.RegisterDto;
import com.example.springboot.exception.EmailJaCadastradoException;
import com.example.springboot.exception.ItemNaoEncontradoException;
import com.example.springboot.models.PessoaModel;
import com.example.springboot.models.enums.PessoaCargo;
import com.example.springboot.repositories.PessoaRepository;


@Service
public class PessoaService {
    
    @Autowired
    private PessoaRepository pessoaRepository;

     @Autowired
    private ViaCepService viaCepService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public PessoaModel register(RegisterDto data) {
        // Verifica se o email já existe
        if (pessoaRepository.findByEmail(data.email()) != null) {
            throw new EmailJaCadastradoException("Email já cadastrado!");
        }

        // Criptografa a senha
        String encryptedPassword = passwordEncoder.encode(data.senha());

        // Se for o primeiro cadastro, será ADMIN. Caso contrário, será USER
        PessoaCargo cargo = pessoaRepository.count() == 0
                ? PessoaCargo.ADMIN
                : PessoaCargo.USER;

        PessoaModel pessoa = new PessoaModel(data.email(), encryptedPassword, cargo);

        return pessoaRepository.save(pessoa);
    }

    // Método para salvar um pessoa POST
    public PessoaResponseDto savePessoa (PessoaRecordDto pessoaRecordDto) {
        var pessoaModel = new PessoaModel();
        BeanUtils.copyProperties(pessoaRecordDto, pessoaModel);
        pessoaModel.setSenha(passwordEncoder.encode(pessoaModel.getSenha()));

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
            throw new ItemNaoEncontradoException("Pessoa com id " + id + " não encontrada");
        }
        return converterParaResponseDto(pessoaModelOptional.get());
    }

    // Método para atualizar um pessoa PUT
    public PessoaResponseDto updatePessoa(long id, PessoaRecordDto pessoaRecordDto) {
        Optional<PessoaModel> pessoaModelOptional = pessoaRepository.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            throw new ItemNaoEncontradoException("Pessoa com id " + id + " não encontrada");
        }
        PessoaModel pessoaModel = pessoaModelOptional.get();
        BeanUtils.copyProperties(pessoaRecordDto, pessoaModel, "senha");

        var endereco = viaCepService.buscaCepResponseDto(pessoaRecordDto.cep());
        if (endereco != null) {
            pessoaModel.setLogradouro(endereco.logradouro());
            pessoaModel.setComplemento(endereco.complemento());
            pessoaModel.setBairro(endereco.bairro());
            pessoaModel.setLocalidade(endereco.localidade());
            pessoaModel.setUf(endereco.uf());
        }

        if (pessoaRecordDto.senha() != null && !pessoaRecordDto.senha().isBlank()) {
        pessoaModel.setSenha(passwordEncoder.encode(pessoaRecordDto.senha()));
        }
        PessoaModel uptadPessoaModel = pessoaRepository.save(pessoaModel);

        return converterParaResponseDto(uptadPessoaModel);
    }

    // Método para deletar um pessoa DELETE
    public void deletePessoa(long id) {
        Optional<PessoaModel> pessoaModelOptional = pessoaRepository.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            throw new ItemNaoEncontradoException("Pessoa com id " + id + " não encontrada");
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