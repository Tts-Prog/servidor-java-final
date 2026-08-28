package com.labanta.servidorlocal.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(ServicoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleServicoNaoEncontradoException(ServicoNaoEncontradoException ex){
        // Enviar um aviso ao administrador da plataforma
        log.warn("Tentativa de acesso a um recurso inexistente: {}", ex.getMessage());

        // JSON hashmap
        Map<String, String> resposta = new HashMap<>();
        resposta.put("erro", "Recurso nao encontrado");
        resposta.put("detalhes", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Argumento inválido recebido: {}", ex.getMessage());

        Map<String, String> resposta = new HashMap<>();
        resposta.put("erro", "Pedido inválido");
        resposta.put("detalhes", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }
    @ExceptionHandler(UtilizadorExistenteException.class)
    public ResponseEntity<Map<String, String>> handleUtilizadorExistenteException(UtilizadorExistenteException ex) {
        log.warn("Tentativa de registo com username duplicado: {}", ex.getMessage());

        Map<String, String> resposta = new HashMap<>();
        resposta.put("erro", "Registo inválido");
        resposta.put("detalhes", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }
    
}
