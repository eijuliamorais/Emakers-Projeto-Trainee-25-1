package com.example.springboot.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.springboot.repositories.LivroRepository;
import com.example.springboot.dtos.LivroRecordDto;
import com.example.springboot.models.LivroModel;


import java.util.List;
import java.util.Optional;



@Service
public class LivroService {
    
    @Autowired
    private LivroRepository livroRepository;

    // Método para salvar um livro
    public LivroModel saveLivro (LivroRecordDto livroRecordDto) {
        var livroModel = new LivroModel();
        BeanUtils.copyProperties(livroRecordDto, livroModel);
        return livroRepository.save(livroModel);
    }


    // Método para listar todos os livro
    public List<LivroModel> listaTodos() {
        return livroRepository.findAll();
    }

    // Método para listar um livro
    public LivroModel getLivroById(@PathVariable long id) {
        Optional<LivroModel> livroModelOptional = livroRepository.findById(id);
        if (!livroModelOptional.isPresent()) {
            throw new RuntimeException("Livro não encontrado");
        }
        return livroModelOptional.get();
    }

    // Método para atualizar um livro
    public LivroModel updateLivro(long id, LivroRecordDto livroRecordDto) {
        Optional<LivroModel> livroModelOptional = livroRepository.findById(id);
        if (!livroModelOptional.isPresent()) {
            throw new RuntimeException("Livro não encontrado");
        }
        var livroModel = livroModelOptional.get();
        BeanUtils.copyProperties(livroRecordDto, livroModel);
        return livroRepository.save(livroModel);
    }

    // Método para deletar um livro
    public void deleteLivro(long id) {
        Optional<LivroModel> livroModelOptional = livroRepository.findById(id);
        if (!livroModelOptional.isPresent()) {
            throw new RuntimeException("Livro não encontrado");
        }
        livroRepository.delete(livroModelOptional.get());
    }

    

}
