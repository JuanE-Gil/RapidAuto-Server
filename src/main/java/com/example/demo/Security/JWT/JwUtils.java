package com.example.demo.Security.JWT;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwUtils {

    @Value("${jwt.secret.key}")
    private String secretKey; //Firma el proyecto

    @Value("${jwt.time.expiration}")
    private String timeExpiration; //Tiempo de expiracion del token

    public String generateAccessToken(String Username) {
        return Jwts.builder()
                .setSubject(Username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Obtener firma del token
    private Key getSignatureKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);// aqui se desencripta la llave
        return Keys.hmacShaKeyFor(keyBytes);//Y la volvemos a encriptar para generar un nuevo token
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignatureKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage()); // this will help in debugging
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()//Lee la clave
                .setSigningKey(getSignatureKey())//le pasamos la firma porque todos los token estan firmados
                .build()
                .parseClaimsJws(token)//Le pasamos el token
                .getBody();
    }
}
