package tech.steve.essaie.service;

import io.jsonwebtoken.Claims;
import tech.steve.essaie.model.Jwt;
import tech.steve.essaie.model.Utilisateur;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public interface JwtService {

    public Jwt tokenByValue(String value);

    public Map<String, String> generate(String username);

    public boolean isTokenExpired(String token);

    public void disableTokens(Utilisateur utilisateur);

    public String extractUsername(String token);


    public Date getExpirationDateFromToken(String token);


    public Claims getAllClaims(String token);

    public Map<String, String> generateJwt(Utilisateur utilisateur);

    public Key getKey();

    public void deconnexion();

    public void removeUselessJwt();

    public Map<String, String> refreshToken(Map<String, String> refreshTokenRequest);
}
