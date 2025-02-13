package br.com.marmitaria.config.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.marmitaria.entity.usuario.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration; 

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String gerarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getId().toString()).claim("email", usuario.getEmail().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean tokenValido(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            Date expirationDate = claims.getExpiration();
            if (expirationDate != null) {
                return new Date().before(expirationDate);
            }
            String subject = claims.getSubject();
            return subject != null;
        }
        return false;
    }

    public String getClaim(String token, String claimName) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.get(claimName).toString();
        }
        return null;
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(getSecretKey())
                       .build()
                       .parseClaimsJws(token)
                       .getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
