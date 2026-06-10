package com.biblioteca.repository;

import com.biblioteca.model.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Long> {
    Optional<Obra> findByCodigo(String codigo);
    List<Obra> findByTituloContainingIgnoreCase(String titulo);
    List<Obra> findByAutorContainingIgnoreCase(String autor);
    List<Obra> findByGeneroContainingIgnoreCase(String genero);
}
