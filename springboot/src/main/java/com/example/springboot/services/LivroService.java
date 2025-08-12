package com.example.springboot.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.springboot.dtos.LivroRecordDto;
import com.example.springboot.dtos.LivroResponseDto;
import com.example.springboot.models.LivroModel;
import com.example.springboot.repositories.LivroRepository;

import jakarta.persistence.EntityNotFoundException;



@Service
public class LivroService {
    
    @Autowired
    private LivroRepository livroRepository;

    // Método para salvar um livro POST
    public LivroResponseDto saveLivro (LivroRecordDto livroRecordDto) {
        var livroModel = new LivroModel();
        BeanUtils.copyProperties(livroRecordDto, livroModel);
        LivroModel savedLivro = livroRepository.save(livroModel);
        return converterParaResponseDto(savedLivro);
    }


    // Método para listar todos os livro GET
    public List<LivroResponseDto> listaTodos() {
        return livroRepository.findAll().stream()
            .map(this::converterParaResponseDto)
            .collect(Collectors.toList());
    }

    public LivroResponseDto getLivroById(long id) {
        Optional<LivroModel> livroModelOptional = livroRepository.findById(id);
        if (!livroModelOptional.isPresent()) {
            throw new EntityNotFoundException("Livro com id " + id + " não encontrado");
        }
      return converterParaResponseDto(livroModelOptional.get());
    }

    // Método para atualizar um livro PUT
    public LivroResponseDto updateLivro(long id, LivroRecordDto livroRecordDto) {
        Optional<LivroModel> livroModelOptional = livroRepository.findById(id);
        if (!livroModelOptional.isPresent()) {
            throw new EntityNotFoundException("Livro com id " + id + " não encontrado");
        }
        LivroModel livroModel = livroModelOptional.get();
        BeanUtils.copyProperties(livroRecordDto, livroModel);
        LivroModel updatedLivro = livroRepository.save(livroModel);
        return converterParaResponseDto(updatedLivro);
    }

    // Método para deletar um livro DELETE
    public void deleteLivro(long id) {
        Optional<LivroModel> livroModelOptional = livroRepository.findById(id);
        if (!livroModelOptional.isPresent()) {
            throw new EntityNotFoundException("Livro com id " + id + " não encontrado");
        }
        livroRepository.delete(livroModelOptional.get());
    }

    private LivroResponseDto converterParaResponseDto(LivroModel l) {
    return new LivroResponseDto(
        l.getIdLivro(),
        l.getTitulo(),
        l.getAutor(),
        l.getEditora(),
        l.getDataPublicacao(),
        l.getGenero(),
        l.getQuantidade()
    );
    }

    

}
