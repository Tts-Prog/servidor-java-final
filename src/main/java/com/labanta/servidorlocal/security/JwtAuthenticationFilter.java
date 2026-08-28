package com.labanta.servidorlocal.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        // Se não há cabeçalho, ou não começa por "Bearer ", deixar passar sem autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extrair o token (ignorar os primeiros 7 caracteres: "Bearer ")
        String token = authHeader.substring(7);

        // Ignorar tokens vazios ou "undefined" (ex: frontend mal configurado)
        if (token.isEmpty() || token.equals("undefined")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extrair o username do token (isto também valida a assinatura e a expiração)
            String username = jwtService.extrairUsername(token);

            // Se o username é válido e ainda não há autenticação no contexto
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Dizer ao Spring que este utilizador está autenticado
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            // Token inválido ou expirado — não autenticar, o Spring vai devolver 401
        }

        filterChain.doFilter(request, response);
    }
}
