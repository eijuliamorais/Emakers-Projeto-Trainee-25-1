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

        if (livro.getQuantidade() <= 0) {
            throw new IllegalStateException("Não há exemplares disponíveis para empréstimo");
        }

        livro.setQuantidade(livro.getQuantidade()-1);
        livroRepository.save(livro);

        // Criar modelo de empréstimo
        EmprestimoModel emprestimo = new EmprestimoModel();
        emprestimo.setLivro(livro);
        emprestimo.setPessoa(pessoa);
        emprestimo.setDataEmprestimo(dto.dataEmprestimo());
        emprestimo.setPrevisaoDevolucao(dto.previsaoDevolucao().plusDays(7));
        emprestimo.setStatus(dto.status() != null ? dto.status() : EmprestimoStatus.EMPRESTADO);

        // Salvar no banco
        EmprestimoModel salvo = emprestimoRepository.save(emprestimo);

        // Criar DTO de resposta
        return converterParaResponseDto(salvo);
    }

 
    // Método para devolver um livro PUT
    public EmprestimoResponseDto marcarComoDevolvido(Long id) {
    EmprestimoModel emprestimo = emprestimoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

    emprestimo.setDataDevolucao(LocalDate.now());
    emprestimo.setStatus(EmprestimoStatus.DEVOLVIDO);

    LivroModel livro = emprestimo.getLivro();
    livro.setQuantidade(livro.getQuantidade() +1);
    livroRepository.save(livro);
    
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
        .filter(e -> calcularStatusAtual(e) == status)
        .map(this::converterParaResponseDto)
        .toList();
    }

    public EmprestimoResponseDto buscarPorId(Long id) {
    EmprestimoModel emprestimo = emprestimoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

    return converterParaResponseDto(emprestimo);
    }


    private EmprestimoResponseDto converterParaResponseDto(EmprestimoModel e) {
    return new EmprestimoResponseDto(
        e.getIdEmprestimo(),
        e.getPessoa().getNome(),
        e.getLivro().getTitulo(),
        e.getDataEmprestimo(),
        e.getDataDevolucao(),
        e.getPrevisaoDevolucao(),
        calcularStatusAtual(e)
    );
    }

    private EmprestimoStatus calcularStatusAtual (EmprestimoModel emprestimo){
        if(emprestimo.getStatus() == EmprestimoStatus.DEVOLVIDO){
            return EmprestimoStatus.DEVOLVIDO;
        }
        if(emprestimo.getPrevisaoDevolucao() != null && LocalDate.now().isAfter(emprestimo.getPrevisaoDevolucao())){
            return EmprestimoStatus.ATRASADO;
        }
        return emprestimo.getStatus();
    }

}
