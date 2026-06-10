package com.biblioteca.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObraRequest {
    private String titulo;
    private String autor;
    private String codigo;
    private String genero;
    private String tipo;
    private Integer quantidadeTotal;
}
