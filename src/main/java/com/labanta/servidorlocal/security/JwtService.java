package com.labanta.servidorlocal.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    // Chave secreta para assinar o token (mínimo 256 bits para HS256)
    private final SecretKey chaveSecreta = Keys.hmacShaKeyFor(
            "${JWT_SECRET}".getBytes()
    );

    public String gerarToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .signWith(chaveSecreta)
                .compact();
    }

    public String extrairUsername(String token) {
        return Jwts.parser()
                .verifyWith(chaveSecreta)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
