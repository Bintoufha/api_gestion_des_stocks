package com.bintoufha.gestionStocks.utils;

import com.bintoufha.gestionStocks.model.auth.ExtendedUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey; // ✅ à ajouter en haut
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtUtils {

    @Value("${jsonwebtoken.jwt.secret}")
    private String secretKey;

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 heures

    // ✅ Génération du token
    public String generateToken(ExtendedUser userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("idEntreprise", userDetails.getIdEntreprise());
        claims.put("UserUuid", userDetails.getUuid());
        return buildToken(claims, userDetails.getUsername());
    }

    private String buildToken(Map<String, Object> claims, String subject) {
        Key key = getSigningKey();
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key) // ✅ plus besoin de préciser HS256, il est déduit de la clé
                .compact();
    }

    // ✅ Extraction d’un claim spécifique
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractIdEntreprise(String token) {
        return extractClaim(token, claims -> claims.get("idEntreprise", String.class));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // ✅ Extraction complète des claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()  // nouvelle API jjwt 0.13.x
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ✅ Clé de signature correcte
private javax.crypto.SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
}

    // ✅ Vérification du token
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("⛔ Token expiré : {}", e.getMessage());
        } catch (JwtException e) {
            log.warn("⚠️ Token invalide : {}", e.getMessage());
        } catch (Exception e) {
            log.error("❌ Erreur de parsing du token : {}", e.getMessage());
        }
        return false;
    }
}
