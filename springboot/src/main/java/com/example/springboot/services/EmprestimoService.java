package com.example.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot.models.EmprestimoModel;
import com.example.springboot.repositories.EmprestimoRepository;
import com.example.springboot.repositories.LivroRepository;
import com.example.springboot.repositories.PessoaRepository;

import java.util.Date;
import java.util.List;

@Service
public class EmprestimoService {
    
    @Autowired
    private EmprestimoRepository emprestimoRepository;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private PessoaRepository pessoaRepository;

    // Método para realizar um empréstimo
    public EmprestimoModel realizarEmprestimo(long idLivro, long idPessoa) {
        var livro = livroRepository.findById(idLivro)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        var pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        // Verifica se o livro já está emprestado
        boolean estaEmprestado = emprestimoRepository.existsByLivroAndAtivo(livro, true);
        if (estaEmprestado) {
            throw new RuntimeException("Livro já está emprestado");
        }
        // Cria um novo empréstimo
        var emprestimo = new EmprestimoModel();
        emprestimo.setLivro(livro);
        emprestimo.setPessoa(pessoa);
        emprestimo.setDataEmprestimo(new Date());
        emprestimo.setAtivo(true);

        return emprestimoRepository.save(emprestimo);
    }

    // Método para devolver um livro
    public EmprestimoModel devolverLivro(long idEmprestimo) {
        var emprestimo = emprestimoRepository.findById(idEmprestimo)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        if (!emprestimo.isAtivo()) {
            throw new RuntimeException("Empréstimo já foi devolvido");
        }

        emprestimo.setAtivo(false);
        emprestimo.setDataDevolucao(new Date());
        return emprestimoRepository.save(emprestimo);

    }

    // Método para listar todos os empréstimos
    public List<EmprestimoModel> listarEmprestimos() {
        return emprestimoRepository.findAll();
    }

    // Método para buscar um empréstimo por ID
    public EmprestimoModel buscarEmprestimoPorId(long id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
    }   

    // Método para listar empréstimos ativos
    public List<EmprestimoModel> listarEmprestimosAtivos() {    
        return emprestimoRepository.findAll().stream()
                .filter(EmprestimoModel::isAtivo)
                .toList();
    }

    // Método para listar empréstimos por pessoa
    public List<EmprestimoModel> listarEmprestimosPorPessoa(long idPessoa) {
        return emprestimoRepository.findAll().stream()
                .filter(emprestimo -> emprestimo.getPessoa().getIdPessoa() == idPessoa)
                .toList();
    }

    // Método para listar empréstimos por livro
    public List<EmprestimoModel> listarEmprestimosPorLivro(long idLivro){
        return emprestimoRepository.findAll().stream()
                .filter(emprestimo -> emprestimo.getLivro().getIdLivro() == idLivro)
                .toList();
    }

    // Método para verificar se um livro está emprestado
    public boolean LivroEmprestado(long idLivro) {
        return emprestimoRepository.existsByLivroAndAtivo(livroRepository.findById(idLivro)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado")), true);
    }
    
    // Método para deletar um empréstimo
    public void deleteEmprestimo(long id) {
        var emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
        emprestimoRepository.delete(emprestimo);
    }




}
