package com.biblioteca.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean sucesso;
    private String mensagem;
    private T dados;

    public static <T> ApiResponse<T> ok(T dados) {
        return new ApiResponse<>(true, null, dados);
    }

    public static <T> ApiResponse<T> ok(String mensagem) {
        return new ApiResponse<>(true, mensagem, null);
    }

    public static <T> ApiResponse<T> erro(String mensagem) {
        return new ApiResponse<>(false, mensagem, null);
    }
}
