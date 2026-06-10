package com.biblioteca.service;

import com.biblioteca.exception.RecursoNaoEncontradoException;
import com.biblioteca.exception.RegraDeNegocioException;
import com.biblioteca.model.Obra;
import com.biblioteca.repository.ObraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ObraService {

    private final ObraRepository obraRepository;

    public Obra cadastrar(Obra obra) {
        if (obraRepository.findByCodigo(obra.getCodigo()).isPresent()) {
            throw new RegraDeNegocioException("Já existe uma obra cadastrada com o código informado: " + obra.getCodigo());
        }

        obra.setQuantidadeDisponivel(obra.getQuantidadeTotal());
        return obraRepository.save(obra);
    }

    public Obra buscarPorId(Long id) {
        return obraRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Obra não encontrada: " + id));
    }

    public Obra buscarPorCodigo(String codigo) {
        return obraRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Obra não encontrada com o código: " + codigo));
    }

    public List<Obra> listarTodas() {
        return obraRepository.findAll();
    }

    public List<Obra> buscar(String tipo, String termo) {
        return switch (tipo.toLowerCase()) {
            case "titulo" -> obraRepository.findByTituloContainingIgnoreCase(termo);
            case "autor" -> obraRepository.findByAutorContainingIgnoreCase(termo);
            case "genero" -> obraRepository.findByGeneroContainingIgnoreCase(termo);
            default -> throw new RegraDeNegocioException("Tipo de busca inválido: " + tipo);
        };
    }

    public void remover(Long id) {
        Obra obra = buscarPorId(id);

        int copiasEmprestadas = obra.getQuantidadeTotal() - obra.getQuantidadeDisponivel();
        if (copiasEmprestadas > 0) {
            throw new RegraDeNegocioException("Não é possível remover a obra pois existem " + copiasEmprestadas + " exemplares emprestados (RN07).");
        }

        obraRepository.delete(obra);
    }
}
