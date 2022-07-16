package com.deep.userservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JWTUtil {
    @Value("${app.secret}")
    private String secret;

    //Validate UserName in token and database
    public boolean validateToken(String token,String username){
        String tokenUsername=getUsername(token);
        return tokenUsername.equals(username) && !isTokenExpired(token);
    }


    //Validate Expiration date
    public Boolean isTokenExpired(String token){
        Date expiryDate=getExpiryDate(token);
        return expiryDate.before(new Date(System.currentTimeMillis()));

    }

    //Read Subject
    public String getUsername(String token){
        return getClaims(token).getSubject();
    }


    //Read Exp Date
    public Date getExpiryDate(String token){
        return getClaims(token).getExpiration();
    }


    //Read Claims
    public Claims getClaims(String token){
       return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    //Generate Token
    public String generateToken(String  subject){
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer("Deep")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(25)))
                .signWith(SignatureAlgorithm.HS512,secret.getBytes())
                .compact();
    }

}
