package com.labanta.servidorlocal.service;

import com.labanta.servidorlocal.dto.LoginRequestDTO;
import com.labanta.servidorlocal.dto.RegistoRequestDTO;
import com.labanta.servidorlocal.exception.UtilizadorExistenteException;
import com.labanta.servidorlocal.model.Utilizador;
import com.labanta.servidorlocal.repository.UtilizadorRepository;
import com.labanta.servidorlocal.security.JwtService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UtilizadorRepository utilizadorRepository;
    private final JwtService jwtService;
    private final EmailService emailService;

    public AuthService(UtilizadorRepository utilizadorRepository, JwtService jwtService, EmailService emailService) {
        this.utilizadorRepository = utilizadorRepository;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    // Missão 1 + 2: Registar utilizador (com verificação de duplicados)
    public Utilizador registarUtilizador(RegistoRequestDTO dados) {
        // Verificar se o username já existe (Missão 2)
        utilizadorRepository.findByUsername(dados.getUsername())
                .ifPresent(u -> {
                    throw new UtilizadorExistenteException("Este username já está em uso, por favor escolha outro.");
                });

        // Transformar DTO em Entidade e guardar
        Utilizador novoUtilizador = new Utilizador();
        novoUtilizador.setUsername(dados.getUsername());
        novoUtilizador.setPassword(dados.getPassword());
        novoUtilizador.setEmail(dados.getEmail());

        emailService.enviarEmailBoasVindas(novoUtilizador.getEmail(), novoUtilizador.getUsername());
        return utilizadorRepository.save(novoUtilizador);
    }

    // Missão 3: Login dinâmico com JWT
    public String login(LoginRequestDTO dados) {
        // Procurar utilizador na base de dados
        Utilizador utilizador = utilizadorRepository.findByUsername(dados.getUsername())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas!"));

        // Verificar a password
        if (!utilizador.getPassword().equals(dados.getPassword())) {
            throw new RuntimeException("Credenciais inválidas!");
        }

        // Gerar e devolver o token JWT com o username real
        return jwtService.gerarToken(utilizador.getUsername());
    }
}
