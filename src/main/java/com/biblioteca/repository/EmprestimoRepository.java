package com.biblioteca.repository;

import com.biblioteca.model.Cliente;
import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByClienteAndDevolvidoFalse(Cliente cliente);
    long countByClienteAndDevolvidoFalse(Cliente cliente);
    boolean existsByClienteAndDevolvidoFalseAndDataPrevistaDevolucaoBefore(Cliente cliente, LocalDate data);
    Optional<Emprestimo> findByClienteAndObraAndDevolvidoFalse(Cliente cliente, Obra obra);
    List<Emprestimo> findByCliente(Cliente cliente);
    List<Emprestimo> findByDevolvidoFalseAndDataPrevistaDevolucaoBefore(LocalDate data);
    List<Emprestimo> findByDevolvidoFalse();

    @Query("SELECT e.obra, COUNT(e) FROM Emprestimo e GROUP BY e.obra ORDER BY COUNT(e) DESC")
    List<Object[]> findObrasMaisEmprestadas();
}
