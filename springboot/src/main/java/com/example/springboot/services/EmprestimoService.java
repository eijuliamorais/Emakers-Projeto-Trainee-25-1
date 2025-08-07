package com.example.springboot.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot.dtos.EmprestimoRecordDto;
import com.example.springboot.dtos.EmprestimoResponseDto;
import com.example.springboot.models.EmprestimoModel;
import com.example.springboot.models.LivroModel;
import com.example.springboot.models.PessoaModel;
import com.example.springboot.models.enums.EmprestimoStatus;
import com.example.springboot.repositories.EmprestimoRepository;
import com.example.springboot.repositories.LivroRepository;
import com.example.springboot.repositories.PessoaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmprestimoService {
    
    @Autowired
    private EmprestimoRepository emprestimoRepository;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private PessoaRepository pessoaRepository;

    // Método para salvar um empréstimo POST
    public EmprestimoResponseDto criarEmprestimo(EmprestimoRecordDto dto) {
        // Buscar pessoa
        PessoaModel pessoa = pessoaRepository.findById(dto.idPessoa())
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));

        // Buscar livro
        LivroModel livro = livroRepository.findById(dto.idLivro())
            .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

        // Verificar se o livro já está emprestado
        boolean livroEmprestado = emprestimoRepository.existsByLivroAndStatus(livro, EmprestimoStatus.EMPRESTADO);
        if (livroEmprestado) {
            throw new IllegalStateException("Livro já está emprestado");
        }

        // Criar modelo de empréstimo
        EmprestimoModel emprestimo = new EmprestimoModel();
        emprestimo.setLivro(livro);
        emprestimo.setPessoa(pessoa);
        emprestimo.setDataEmprestimo(dto.dataEmprestimo());
        emprestimo.setDataDevolucao(dto.dataDevolucao());
        emprestimo.setPrevisaoDevolucao(dto.previsaoDevolucao().plusDays(7));
        emprestimo.setStatus(dto.status() != null ? dto.status() : EmprestimoStatus.EMPRESTADO);

        // Salvar no banco
        EmprestimoModel salvo = emprestimoRepository.save(emprestimo);

        // Criar DTO de resposta
        return new EmprestimoResponseDto(
            salvo.getIdEmprestimo(),
            salvo.getPessoa().getNome(),
            salvo.getLivro().getTitulo(),
            salvo.getDataEmprestimo(),
            salvo.getDataDevolucao(),
            salvo.getPrevisaoDevolucao(),
            salvo.getStatus()
        );
    }

 
    // Método para devolver um livro PUT
public EmprestimoResponseDto marcarComoDevolvido(Long id) {
    EmprestimoModel emprestimo = emprestimoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

    emprestimo.setDataDevolucao(LocalDate.now());
    emprestimo.setStatus(EmprestimoStatus.DEVOLVIDO);
    
    emprestimo = emprestimoRepository.save(emprestimo);

    return converterParaResponseDto(emprestimo);
}

public List<EmprestimoResponseDto> listarTodos() {
    return emprestimoRepository.findAll().stream()
        .map(this::converterParaResponseDto)
        .toList();
}

public List<EmprestimoResponseDto> listarPorStatus(EmprestimoStatus status) {
    return emprestimoRepository.findByStatus(status).stream()
        .map(this::converterParaResponseDto)
        .toList();
}

public EmprestimoResponseDto buscarPorId(Long id) {
    EmprestimoModel emprestimo = emprestimoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

    return converterParaResponseDto(emprestimo);
}

// Método auxiliar para reaproveitar conversão
private EmprestimoResponseDto converterParaResponseDto(EmprestimoModel e) {
    return new EmprestimoResponseDto(
        e.getIdEmprestimo(),
        e.getPessoa().getNome(),
        e.getLivro().getTitulo(),
        e.getDataEmprestimo(),
        e.getDataDevolucao(),
        e.getPrevisaoDevolucao(),
        e.getStatus()
    );
}

}
