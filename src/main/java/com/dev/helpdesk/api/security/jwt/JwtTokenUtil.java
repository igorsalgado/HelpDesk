package com.dev.helpdesk.api.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String CLAIM_KEY_USERNAME = "sub";
    private final String CLAIM_KEY_CREATED = "created";
    private final String CLAIM_KEY_EXPIRED = "exp";

    @Value(value = "${jwt.secret}")
    private String secret;

    @Value(value = "${jwt.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token) { // Retorna o username (email) do token JWT
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject(); // Pega o subject do token
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getExpirationDateFromToken(String token) { // Retorna a data de expiração do token JWT
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration(); // Pega a data de expiração do token
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public Claims getClaimsFromToken(String token) { // Retorna as claims do token JWT (corpo do token)
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token) // Pega as claims do token
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Boolean isTokenExpired(String token) { // Verifica se o token está expirado
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) { // Gera um novo token (refresh)
        Map<String, Object> claims = new HashMap<>();

        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername()); // Coloca o username no token
        final Date createdDate = new Date();

        claims.put(CLAIM_KEY_CREATED, createdDate); // Coloca a data de criação no token
        return doGenerateToken(claims);
    }

    public String doGenerateToken(Map<String,Object> claims) { //Metodo auxiliar para gerar o token
        final Date createdDate = (Date) claims.get(CLAIM_KEY_CREATED);
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000); // Pega a data de expiração do token
        return Jwts.builder()
                .setClaims(claims) // Coloca as claims no token
                .setExpiration(expirationDate) // Coloca a data de expiração no token
                .signWith(SignatureAlgorithm.HS512, secret) // Coloca o algoritmo de criptografia e a chave secreta no token
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token) { // Verifica se o token pode ser atualizado
        return (!isTokenExpired(token));
    }

    public String refreshToken(String token) { // Atualiza o token
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token); // Pega as claims do token
            claims.put(CLAIM_KEY_CREATED, new Date()); // Atualiza a data de criação do token
            refreshedToken = doGenerateToken(claims); // Gera um novo token
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) { // Verifica se o token é válido
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token); // Pega o username do token
        return (username.equals(user.getUsername()) && !isTokenExpired(token)); // Verifica se o username do token é igual ao username do usuário e se o token não está expirado
    }
}
