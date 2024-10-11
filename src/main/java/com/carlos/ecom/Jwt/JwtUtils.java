package com.carlos.ecom.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {

    private final SecretKey secret; // Es la encriptacion de nuestra clave en base64
    private static final long EXPIRATION_TIME = 86400000L; //El tiempo de que tiene el guardado el token

    //Metodo para convertir nuestra clave en base64
    public JwtUtils(){
        String secreteString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
        byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
        this.secret = new SecretKeySpec(keyBytes,"HmacSHA256");
    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public java.util.Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(secret).build().parseSignedClaims(token).getPayload());
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new java.util.Date());
    }

    public String generateSecretToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new java.util.Date(System.currentTimeMillis()))
                .expiration(new java.util.Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .signWith(secret)
                .compact();
    }

    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails){
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new java.util.Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .signWith(secret)
                .compact();
    }


    public boolean isTokenValid (String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
