package com.biblioteca.repository;

import com.biblioteca.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByMatricula(String matricula);
    boolean existsByMatricula(String matricula);
}
