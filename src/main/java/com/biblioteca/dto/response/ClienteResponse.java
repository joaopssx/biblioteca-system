package com.biblioteca.dto.response;

import com.biblioteca.model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    private Long id;
    private String nome;
    private String matricula;
    private Boolean bloqueado;
    private int emprestimosAtivos;

    public static ClienteResponse toResponse(Cliente cliente, int emprestimosAtivos) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .matricula(cliente.getMatricula())
                .bloqueado(cliente.getBloqueado())
                .emprestimosAtivos(emprestimosAtivos)
                .build();
    }
}
