package com.biblioteca.service;

import com.biblioteca.exception.RecursoNaoEncontradoException;
import com.biblioteca.exception.RegraDeNegocioException;
import com.biblioteca.model.Cliente;
import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Obra;
import com.biblioteca.repository.ClienteRepository;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.ObraRepository;
import com.biblioteca.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final ClienteRepository clienteRepository;
    private final ObraRepository obraRepository;
    private final ReservaRepository reservaRepository;

    @Transactional
    public Emprestimo emprestar(String matriculaCliente, String codigoObra) {
        Cliente cliente = clienteRepository.findByMatricula(matriculaCliente)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com a matrícula: " + matriculaCliente));

        Obra obra = obraRepository.findByCodigo(codigoObra)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Obra não encontrada com o código: " + codigoObra));

        if (cliente.getBloqueado()) {
            throw new RegraDeNegocioException("RN04: Cliente bloqueado para realizar empréstimos.");
        }

        if (emprestimoRepository.existsByClienteAndDevolvidoFalseAndDataPrevistaDevolucaoBefore(cliente, LocalDate.now())) {
            cliente.setBloqueado(true);
            clienteRepository.save(cliente);
            throw new RegraDeNegocioException("RN04: Cliente possui empréstimo em atraso e foi bloqueado automaticamente.");
        }

        if (emprestimoRepository.countByClienteAndDevolvidoFalse(cliente) >= 3) {
            throw new RegraDeNegocioException("RN03: Limite de 3 empréstimos simultâneos atingido para o cliente.");
        }

        if (obra.getQuantidadeDisponivel() <= 0) {
            throw new RegraDeNegocioException("RN06: Obra indisponível para empréstimo. Sugerimos realizar uma reserva.");
        }

        Emprestimo emprestimo = Emprestimo.builder()
                .cliente(cliente)
                .obra(obra)
                .dataEmprestimo(LocalDate.now())
                .dataPrevistaDevolucao(LocalDate.now().plusDays(7))
                .devolvido(false)
                .multa(0.0)
                .build();

        obra.setQuantidadeDisponivel(obra.getQuantidadeDisponivel() - 1);
        obraRepository.save(obra);

        reservaRepository.findByClienteAndObraAndAtivaTrue(cliente, obra)
                .ifPresent(reserva -> {
                    reserva.setAtiva(false);
                    reservaRepository.save(reserva);
                });

        Emprestimo salvo = emprestimoRepository.save(emprestimo);
        
        log.info("Empréstimo realizado com sucesso. Cliente: {}, Obra: {}", matriculaCliente, codigoObra);
        
        return salvo;
    }
}
