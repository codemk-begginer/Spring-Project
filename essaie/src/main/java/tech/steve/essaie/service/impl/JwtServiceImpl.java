package tech.chilo.avis.securite;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tech.chilo.avis.entite.Jwt;
import tech.chilo.avis.entite.RefreshToken;
import tech.chilo.avis.entite.Utilisateur;
import tech.chilo.avis.repository.JwtRepository;
import tech.chilo.avis.service.UtilisateurService;

import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
@Slf4j
@AllArgsConstructor
@Transactional
@Service
public class JwtService {

    public static final String BEARER = "bearer";
    public static final String REFRESH = "refresh";
    public static final String TOKEN_INVALIDE = "token invalide";
    private JwtRepository jwtRepository;
    private final String ENCRIPTION_KEY = "XbW0yMH928Uy6sZDxdwaASh/OBo+FEUD7yXNYEt4ZzcPTRYCCKk1XokTApCLBWEE";
  private UtilisateurService utilisateurService;

    public Jwt tokenByValue(String value) {
        return this.jwtRepository.findByValeurAndDesactiveAndExpire(
                value,
                false,
                false
        ).orElseThrow(() -> new SqlScriptException("Token invalide ou inconnu"));
    }
    public Map<String, String> generate(String username) {
        Utilisateur utilisateur = (Utilisateur) this.utilisateurService.loadUserByUsername(username);
        this.disableTokens(utilisateur);
        final Map<String, String> jwtMap = new java.util.HashMap<>(this.generateJwt(utilisateur));

        RefreshToken refreshToken = RefreshToken.builder()
                .valeur(UUID.randomUUID().toString())
                .expire(false)
                .creation(Instant.now())
                .expiration(Instant.now().plusMillis(30 *60 *1000))
               .build();

        final Jwt jwt = Jwt
                .builder()
                .valeur(jwtMap.get(BEARER))
                .desactive(false)
                .expire(false)
                .utilisateur(utilisateur)
               .refreshToken(refreshToken)
                .build();

        this.jwtRepository.save(jwt);
       jwtMap.put(REFRESH,  refreshToken.getValeur());
        return jwtMap;
    }

    private void disableTokens(Utilisateur utilisateur) {
        final List<Jwt> jwtList = this.jwtRepository.findUtilisateur(utilisateur.getEmail()).peek(
                jwt -> {
                    jwt.setDesactive(true);
                    jwt.setExpire(true);
                }
        ).collect(Collectors.toList());

        this.jwtRepository.saveAll(jwtList);
    }

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Map<String, String> generateJwt(Utilisateur utilisateur) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 60 * 1000;

        final Map<String, Object> claims = Map.of(
                "nom", utilisateur.getNom(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, utilisateur.getEmail()
        );

        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(utilisateur.getEmail())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of(BEARER, bearer);
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

    public void deconnexion() {
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = this.jwtRepository.findUtilisateurValidToken(
                utilisateur.getEmail(),
                false,
                false
        ).orElseThrow(() -> new RuntimeException(TOKEN_INVALIDE));
        jwt.setExpire(true);
        jwt.setDesactive(true);
        this.jwtRepository.save(jwt);
    }

    @Scheduled(cron = "@daily")
    //@Scheduled(cron = "0 */1 * * * *")
    public void removeUselessJwt() {
        log.info("Suppression des token à {}", Instant.now());
        this.jwtRepository.deleteAllByExpireAndDesactive(true, true);
    }



   public Map<String, String> refreshToken(Map<String, String> refreshTokenRequest) {
        final Jwt jwt = this.jwtRepository.findByRefreshToken(refreshTokenRequest.get(REFRESH)).orElseThrow(()
                -> new RuntimeException(TOKEN_INVALIDE));
       if(jwt.getRefreshToken().isExpire()
               || jwt.getRefreshToken().getExpiration()
               .isBefore(Instant.now())) {
          throw new RuntimeException(TOKEN_INVALIDE);
        }
        this.disableTokens(jwt.getUtilisateur());
       return this.generate(jwt.getUtilisateur().getEmail());
    }



//    public static final String BEARER = "bearer";
//    private JwtRepository jwtRepository;
//
//    private final String ENCRIPTION_KEY = "XbW0yMH928Uy6sZDxdwaASh/OBo+FEUD7yXNYEt4ZzcPTRYCCKk1XokTApCLBWEE";
//    private UtilisateurService utilisateurService;
//
//    public Jwt tokenByValue(String value){
//        return this.jwtRepository.findByValeurAndDesactiveAndExpire(
//                value,
//                true,
//                true
//        ).orElseThrow(() -> new RuntimeException("Token invalide ou inconnue "));
//    }
//
//    public Map< String,String > generate(String username ){
//        Utilisateur utilisateur = (Utilisateur) this.utilisateurService.loadUserByUsername(username);
//
//        Map<String, String> JwtMap = this.generateJwt(utilisateur);
//        Jwt jwt = Jwt.builder().valeur(JwtMap.get(BEARER)).desactive(false)
//                .expire(false)
//                .utilisateur(utilisateur)
//                .build();
//        this.jwtRepository.save(jwt);
//        return JwtMap;
//    }
//
//    public void deconnexion() {
//        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Jwt jwt = this.jwtRepository
//                .findUtilisateurValidToken(utilisateur
//                        .getEmail(),false,false)
//                .orElseThrow(()-> new RuntimeException("Token invalide"));
//        jwt.setExpire(true);
//        jwt.setDesactive(true);
//        this.jwtRepository.save(jwt);
//    }
//
//    public String extractUsername(String token) {
//
//        return  this.getClaim(token,Claims::getSubject);
//    }
//
//    public Boolean isTokenExpired(String token) {
//
//        Date expirationDate =  this.getClaim(token,Claims::getExpiration);
//        return expirationDate.before(new Date());
//    }
//
//    public Jwt tokenByValue(String value) {
//       return this.jwtRepository.findByValeur(value)
//               .orElseThrow(()-> new RuntimeException("Token inconnu"));
//    }
//
//
//
//    private <T> T getClaim(String token, Function<Claims, T> function){
//        Claims claims = getAllClaims(token);
//        return function.apply(claims);
//    }
//
//    private Claims getAllClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(this.getKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//   final long currentTime = System.currentTimeMillis();
//   final long expirationTime = currentTime + 60 * 60 * 1000;
//
//    private Map<String, String> generateJwt(Utilisateur utilisateur) {
//
//        Map<String, Object> claims = Map.of(
//                "nom", utilisateur.getNom(),
//
//                Claims.EXPIRATION,new Date(expirationTime),
//                Claims.SUBJECT, utilisateur.getEmail()
//
//        );
//
//
//        String bearer = Jwts.builder()
//                .setIssuedAt(new Date(currentTime))
//                .setExpiration(new Date(expirationTime))
//                .setSubject(utilisateur.getEmail())
//                .setClaims(claims)
//                .signWith(getKey(), SignatureAlgorithm.HS256)
//                .compact();
//        return Map.of("bearer", bearer);
//    }
//
//    private Key getKey(){
//        byte[] decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);
//        return Keys.hmacShaKeyFor(decoder);
//    }
//
//    private void disableTokens(Utilisateur utilisateur){
//
//        final List<Jwt> jwtList = this.jwtRepository.findUtilisateurValidToken(utilisateur.getEmail()).peek(
//            jwt -> {
//                jwt.setDesactive(false);
//                jwt.setExpire(false);
//            }
//        ).collect(Collectors.toList());
//
//        this.jwtRepository.saveAll(jwtList);
//    }


}
