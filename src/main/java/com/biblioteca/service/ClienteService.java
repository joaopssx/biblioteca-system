package com.biblioteca.service;

import com.biblioteca.exception.RecursoNaoEncontradoException;
import com.biblioteca.exception.RegraDeNegocioException;
import com.biblioteca.model.Cliente;
import com.biblioteca.repository.ClienteRepository;
import com.biblioteca.repository.EmprestimoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EmprestimoRepository emprestimoRepository;

    public Cliente cadastrar(Cliente cliente) {
        if (clienteRepository.existsByMatricula(cliente.getMatricula())) {
            throw new RegraDeNegocioException("Já existe um cliente com a matrícula informada: " + cliente.getMatricula());
        }
        return clienteRepository.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado: " + id));
    }

    public Cliente buscarPorMatricula(String matricula) {
        return clienteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com a matrícula: " + matricula));
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public void remover(Long id) {
        Cliente cliente = buscarPorId(id);
        
        long emprestimosAtivos = emprestimoRepository.countByClienteAndDevolvidoFalse(cliente);
        if (emprestimosAtivos > 0) {
            throw new RegraDeNegocioException("Cliente possui empréstimos ativos");
        }
        
        clienteRepository.delete(cliente);
    }

    public Cliente bloquear(Long id) {
        Cliente cliente = buscarPorId(id);
        cliente.setBloqueado(true);
        return clienteRepository.save(cliente);
    }

    public Cliente desbloquear(Long id) {
        Cliente cliente = buscarPorId(id);
        cliente.setBloqueado(false);
        return clienteRepository.save(cliente);
    }
}
