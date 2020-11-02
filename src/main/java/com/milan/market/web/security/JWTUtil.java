package com.milan.market.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    private static String KEY = "admin";

    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()
                .setSubject( userDetails.getUsername() ) //Asociamos al token el usuario
                .setIssuedAt( new Date() ) // La fecha actual
                .setExpiration( new Date( System.currentTimeMillis() + 1000 * 60 * 60 * 10 ) ) // Fecha de expiracion
                .signWith( SignatureAlgorithm.HS256,  KEY).compact();

    }

    public boolean validateToken( String token, UserDetails userDetails ) {
        /**
         * Para comprobar que el token es correcto (el que esta haciendo la peticion), hay que determinar que el
         * usuario sea el indicado y que no haya expirado
         */
        return userDetails.getUsername().equals( extractUsername( token ) ) && !isTokenExpired( token );

    }

    public String extractUsername( String token ) {
        return getClaims( token ).getSubject();
    }

    public boolean isTokenExpired( String token ) {
        return getClaims( token ).getExpiration().before( new Date() );
    }

    /**
     * Los Claims son los objetos dentro del token
     * @param token
     * @return
     */
    private Claims getClaims( String token ) {
        /**
         * En este return comprobamos si la firma signature es correcta, si no lo es la ejecición termina
         */
        return Jwts.parser().setSigningKey( KEY ).parseClaimsJws( token ).getBody();
    }
}
