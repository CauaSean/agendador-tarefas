package com.caua.agendadortarefas.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret:minha_chave_secreta}")
    private String secret;

    @Value("${jwt.expiration:86400000}")
    private Long expiration;

    // 1. Gera o token
    public String gerarToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC256(secret)); // Use o Algorithm do auth0
    }

    // 2. Extrai o e-mail diretamente
    public String extrairEmailToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret)) // Use o Algorithm do auth0
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            // Aqui você captura o erro específico da biblioteca auth0
            return null;
        }
    }

    // 3. Validação simples
    public boolean isTokenValido(String token) {
        return extrairEmailToken(token) != null;
    }
}