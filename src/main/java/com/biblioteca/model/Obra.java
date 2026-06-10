package com.biblioteca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "obras")
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false, unique = true)
    private String codigo;

    private String genero;

    @Enumerated(EnumType.STRING)
    private TipoObra tipo;

    @Column(nullable = false)
    private Integer quantidadeTotal;

    @Column(nullable = false)
    private Integer quantidadeDisponivel;
}
