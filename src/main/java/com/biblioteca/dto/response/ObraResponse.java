package com.biblioteca.dto.response;

import com.biblioteca.model.Obra;
import com.biblioteca.model.TipoObra;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObraResponse {
    private Long id;
    private String titulo;
    private String autor;
    private String codigo;
    private String genero;
    private TipoObra tipo;
    private Integer quantidadeTotal;
    private Integer quantidadeDisponivel;
    private Integer copiasEmprestadas;

    public static ObraResponse toResponse(Obra obra) {
        return ObraResponse.builder()
                .id(obra.getId())
                .titulo(obra.getTitulo())
                .autor(obra.getAutor())
                .codigo(obra.getCodigo())
                .genero(obra.getGenero())
                .tipo(obra.getTipo())
                .quantidadeTotal(obra.getQuantidadeTotal())
                .quantidadeDisponivel(obra.getQuantidadeDisponivel())
                .copiasEmprestadas(obra.getQuantidadeTotal() - obra.getQuantidadeDisponivel())
                .build();
    }
}
