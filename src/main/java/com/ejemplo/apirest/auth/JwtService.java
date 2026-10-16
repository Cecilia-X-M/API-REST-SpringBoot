package com.ejemplo.apirest.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET =
            "mi-clave-secreta-para-jwt-api-rest-2026-muy-segura";

    private final SecretKey key;

    public JwtService() {

        this.key = Keys.hmacShaKeyFor(
                SECRET.getBytes(StandardCharsets.UTF_8)
        );
    }


    // ==========================================
    // GENERAR TOKEN
    // ==========================================

    public String generarToken(String correo) {

        long ahora = System.currentTimeMillis();

        return Jwts.builder()
                .subject(correo)
                .issuedAt(new Date(ahora))
                .expiration(new Date(ahora + 86400000))
                .signWith(key)
                .compact();
    }


    // ==========================================
    // EXTRAER CORREO
    // ==========================================

    public String extraerCorreo(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


    // ==========================================
    // VALIDAR TOKEN
    // ==========================================

    public boolean esTokenValido(
            String token,
            UserDetails userDetails) {

        String correo = extraerCorreo(token);

        return correo.equals(userDetails.getUsername())
                && !estaExpirado(token);
    }


    // ==========================================
    // VERIFICAR EXPIRACIÓN
    // ==========================================

    private boolean estaExpirado(String token) {

        Date expiracion =
                Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getExpiration();

        return expiracion.before(new Date());
    }
}