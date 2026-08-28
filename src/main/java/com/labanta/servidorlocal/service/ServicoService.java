package com.labanta.servidorlocal.service;

import com.labanta.servidorlocal.exception.ServicoNaoEncontradoException;
import com.labanta.servidorlocal.model.ServicoModel;
import com.labanta.servidorlocal.repository.ServicoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    private static final Logger log = LoggerFactory.getLogger(ServicoService.class);

    private final ServicoRepository repositorio;

    public ServicoService(ServicoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public Page<ServicoModel> listarTodos(Pageable pageable) {
        return repositorio.findAll(pageable);
    }

    public ServicoModel criarServico(ServicoModel novoServico) {
        return repositorio.save(novoServico);
    }

    public ServicoModel buscarServicoPorId(Long id) {
        log.info("A procurar serviço com ID: {}", id);
        return repositorio.findById(id)
                .orElseThrow(() -> new ServicoNaoEncontradoException("O serviço com o ID " + id + " não existe no catálogo."));
    }

    public List<ServicoModel> aplicarDescontoEmAtivos(Double percentagem) {
        if (percentagem < 0 || percentagem > 100) {
            throw new IllegalArgumentException("Desconto inválido.");
        }

        log.info("A aplicar desconto de {}% nos serviços ativos.", percentagem);

        List<ServicoModel> ativos = repositorio.findByEstaAtivoTrue();

        for (ServicoModel servico : ativos) {
            double valorDesconto = (servico.getPreco() * percentagem) / 100;
            servico.setPrecoComDesconto(servico.getPreco() - valorDesconto);
        }

        return repositorio.saveAll(ativos);
    }

    // Missão 4: Motor de busca por título
    public List<ServicoModel> pesquisarPorTitulo(String termo) {
        return repositorio.findByTituloContainingIgnoreCase(termo);
    }
}
