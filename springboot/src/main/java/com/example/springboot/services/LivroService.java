package com.example.springboot.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.springboot.dtos.LivroRecordDto;
import com.example.springboot.dtos.LivroResponseDto;
import com.example.springboot.models.LivroModel;
import com.example.springboot.repositories.LivroRepository;



@Service
public class LivroService {
    
    @Autowired
    private LivroRepository livroRepository;

    // Método para salvar um livro POST
    public LivroResponseDto saveLivro (LivroRecordDto livroRecordDto) {
        var livroModel = new LivroModel();
        BeanUtils.copyProperties(livroRecordDto, livroModel);
        LivroModel savedLivro = livroRepository.save(livroModel);
        return new LivroResponseDto(
            savedLivro.getIdLivro(),
            savedLivro.getTitulo(),
            savedLivro.getAutor(),
            savedLivro.getEditora(), 
            savedLivro.getDataPublicacao(),
            savedLivro.getGenero()
        );
    }


    // Método para listar todos os livro GET
    public List<LivroResponseDto> listaTodos() {
        return livroRepository.findAll().stream()
            .map(livro -> new LivroResponseDto(
                livro.getIdLivro(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getEditora(),
                livro.getDataPublicacao(),
                livro.getGenero()
            ))
            .collect(Collectors.toList());
    }

    // Método para listar um livro GET
    public LivroResponseDto getLivroById(@PathVariable long id) {
        Optional<LivroModel> livroModelOptional = livroRepository.findById(id);
        if (!livroModelOptional.isPresent()) {
            throw new RuntimeException("Livro não encontrado");
        }
        return new LivroResponseDto(
            livroModelOptional.get().getIdLivro(),
            livroModelOptional.get().getTitulo(),
            livroModelOptional.get().getAutor(),
            livroModelOptional.get().getEditora(),
            livroModelOptional.get().getDataPublicacao(),
            livroModelOptional.get().getGenero()
        );
    }

    // Método para atualizar um livro PUT
    public LivroResponseDto updateLivro(long id, LivroRecordDto livroRecordDto) {
        Optional<LivroModel> livroModelOptional = livroRepository.findById(id);
        if (!livroModelOptional.isPresent()) {
            throw new RuntimeException("Livro não encontrado");
        }
        LivroModel livroModel = livroModelOptional.get();
        BeanUtils.copyProperties(livroRecordDto, livroModel);
        LivroModel updatedLivro = livroRepository.save(livroModel);
        return new LivroResponseDto(
            updatedLivro.getIdLivro(),
            updatedLivro.getTitulo(),
            updatedLivro.getAutor(),
            updatedLivro.getEditora(),
            updatedLivro.getDataPublicacao(),
            updatedLivro.getGenero()
        );
    }

    // Método para deletar um livro DELETE
    public void deleteLivro(long id) {
        Optional<LivroModel> livroModelOptional = livroRepository.findById(id);
        if (!livroModelOptional.isPresent()) {
            throw new RuntimeException("Livro não encontrado");
        }
        livroRepository.delete(livroModelOptional.get());
    }

    

}
