package com.biblioteca.controller;

import com.biblioteca.dto.request.ObraRequest;
import com.biblioteca.dto.response.ApiResponse;
import com.biblioteca.dto.response.ObraResponse;
import com.biblioteca.model.Obra;
import com.biblioteca.model.TipoObra;
import com.biblioteca.service.ObraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/obras")
@RequiredArgsConstructor
public class ObraController {

    private final ObraService obraService;

    @PostMapping
    public ResponseEntity<ApiResponse<ObraResponse>> cadastrar(@RequestBody ObraRequest request) {
        Obra obra = Obra.builder()
                .titulo(request.getTitulo())
                .autor(request.getAutor())
                .codigo(request.getCodigo())
                .genero(request.getGenero())
                .tipo(request.getTipo() != null ? TipoObra.valueOf(request.getTipo().toUpperCase()) : null)
                .quantidadeTotal(request.getQuantidadeTotal())
                .build();

        Obra obraCadastrada = obraService.cadastrar(obra);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(ObraResponse.toResponse(obraCadastrada)));
    }

    @GetMapping
    public ApiResponse<List<ObraResponse>> listarTodas() {
        List<ObraResponse> obras = obraService.listarTodas().stream()
                .map(ObraResponse::toResponse)
                .toList();
        return ApiResponse.ok(obras);
    }

    @GetMapping("/{id}")
    public ApiResponse<ObraResponse> buscarPorId(@PathVariable Long id) {
        Obra obra = obraService.buscarPorId(id);
        return ApiResponse.ok(ObraResponse.toResponse(obra));
    }

    @GetMapping("/busca")
    public ApiResponse<List<ObraResponse>> buscar(@RequestParam String tipo, @RequestParam String termo) {
        List<ObraResponse> obras = obraService.buscar(tipo, termo).stream()
                .map(ObraResponse::toResponse)
                .toList();
        return ApiResponse.ok(obras);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> remover(@PathVariable Long id) {
        obraService.remover(id);
        return ApiResponse.ok("Obra removida com sucesso");
    }
}
