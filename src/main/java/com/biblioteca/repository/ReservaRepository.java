package com.biblioteca.repository;

import com.biblioteca.model.Cliente;
import com.biblioteca.model.Obra;
import com.biblioteca.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByClienteAndAtivaTrue(Cliente cliente);
    Optional<Reserva> findByClienteAndObraAndAtivaTrue(Cliente cliente, Obra obra);
    boolean existsByClienteAndObraAndAtivaTrue(Cliente cliente, Obra obra);
}
