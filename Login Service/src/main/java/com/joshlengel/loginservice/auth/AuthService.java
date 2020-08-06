package com.joshlengel.loginservice.auth;

import com.joshlengel.loginservice.db.UserAccount;
import io.jsonwebtoken.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.crypto.spec.SecretKeySpec;
import javax.inject.Singleton;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.Date;

@Singleton
public class AuthService {

    @ConfigProperty(name = "security.privateKey")
    @NotNull
    @NotEmpty
    String privateKey;

    public String getToken(UserAccount account) {
        long millis = System.currentTimeMillis();
        Date now = new Date(millis);
        Date exp = new Date(millis + 30 * 1000);

        Key privateKey = new SecretKeySpec(this.privateKey.getBytes(), "HmacSHA512");

        return Jwts.builder()
                .setId(String.valueOf(account.id))
                .setSubject(account.username)
                .setIssuer("com.joshlengel.auth.AuthService")
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, privateKey)
                .compact();
    }

    public Jws<Claims> validateToken(String token) throws WebApplicationException {
        try {
            return Jwts.parser()
                    .setSigningKey(this.privateKey.getBytes())
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new WebApplicationException("Token expired", Response.Status.GATEWAY_TIMEOUT);
        }
    }
}
