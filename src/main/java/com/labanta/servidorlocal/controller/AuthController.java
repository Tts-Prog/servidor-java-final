package com.labanta.servidorlocal.controller;

import com.labanta.servidorlocal.dto.LoginRequestDTO;
import com.labanta.servidorlocal.dto.RegistoRequestDTO;
import com.labanta.servidorlocal.model.Utilizador;
import com.labanta.servidorlocal.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Missão 3: Login dinâmico (já não é hardcoded)
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequestDTO pedido) {
        String token = authService.login(pedido);

        Map<String, String> resposta = new HashMap<>();
        resposta.put("token", token);
        return resposta;
    }

    // Missão 1: Registo de utilizadores
    @PostMapping("/registar")
    public Utilizador registar(@RequestBody RegistoRequestDTO dados) {
        return authService.registarUtilizador(dados);
    }
}
