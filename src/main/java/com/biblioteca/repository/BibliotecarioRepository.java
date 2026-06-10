package com.biblioteca.repository;

import com.biblioteca.model.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {
    Optional<Bibliotecario> findByUsername(String username);
}
