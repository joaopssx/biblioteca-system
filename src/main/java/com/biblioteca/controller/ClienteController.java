package com.biblioteca.controller;

import com.biblioteca.dto.request.ClienteRequest;
import com.biblioteca.dto.response.ApiResponse;
import com.biblioteca.dto.response.ClienteResponse;
import com.biblioteca.model.Cliente;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final EmprestimoRepository emprestimoRepository;

    private int getEmprestimosAtivos(Cliente cliente) {
        return (int) emprestimoRepository.countByClienteAndDevolvidoFalse(cliente);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClienteResponse>> cadastrar(@RequestBody ClienteRequest request) {
        Cliente cliente = Cliente.builder()
                .nome(request.getNome())
                .matricula(request.getMatricula())
                .build();

        Cliente salvo = clienteService.cadastrar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(ClienteResponse.toResponse(salvo, 0)));
    }

    @GetMapping
    public ApiResponse<List<ClienteResponse>> listarTodos() {
        List<ClienteResponse> clientes = clienteService.listarTodos().stream()
                .map(c -> ClienteResponse.toResponse(c, getEmprestimosAtivos(c)))
                .toList();
        return ApiResponse.ok(clientes);
    }

    @GetMapping("/{id}")
    public ApiResponse<ClienteResponse> buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ApiResponse.ok(ClienteResponse.toResponse(cliente, getEmprestimosAtivos(cliente)));
    }

    @GetMapping("/matricula/{matricula}")
    public ApiResponse<ClienteResponse> buscarPorMatricula(@PathVariable String matricula) {
        Cliente cliente = clienteService.buscarPorMatricula(matricula);
        return ApiResponse.ok(ClienteResponse.toResponse(cliente, getEmprestimosAtivos(cliente)));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> remover(@PathVariable Long id) {
        clienteService.remover(id);
        return ApiResponse.ok("Cliente removido com sucesso");
    }

    @PatchMapping("/{id}/bloquear")
    public ApiResponse<ClienteResponse> bloquear(@PathVariable Long id) {
        Cliente cliente = clienteService.bloquear(id);
        return ApiResponse.ok(ClienteResponse.toResponse(cliente, getEmprestimosAtivos(cliente)));
    }

    @PatchMapping("/{id}/desbloquear")
    public ApiResponse<ClienteResponse> desbloquear(@PathVariable Long id) {
        Cliente cliente = clienteService.desbloquear(id);
        return ApiResponse.ok(ClienteResponse.toResponse(cliente, getEmprestimosAtivos(cliente)));
    }
}
