package com.msglearning.javabackend.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenService {

    private Logger LOG = LoggerFactory.getLogger(this.getClass().getName());

    public static final String NAME = "name";
    public static final String ID = "id";
    public static final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS256);
    static final byte[] secretBytes = secret.getEncoded();
    public static final String base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);
    private static final String TOKEN_PREFIX = "{\"token\":\"";
    private static final String TOKEN_SUFFIX = "\"}";

    public String createTokenHeader(final String email) {
        String token = this.createJWT(ID, email, 21600000L);
        return TOKEN_PREFIX + token + TOKEN_SUFFIX;
    }

    private String createJWT(String id, String name, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .claim(NAME, name)
                .signWith(signatureAlgorithm, base64SecretBytes);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            builder.setExpiration(new Date(expMillis));
        }

        return builder.compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(base64SecretBytes)
                    .parseClaimsJws(token);
            Claims claimsBody = claims.getBody();
            StringBuilder tLog = new StringBuilder();
            logClaims(claimsBody, tLog);
            return !claims.getBody().getExpiration().before(new Date());
        }
        catch (JwtException e) {
            LOG.error("Access denied: " + e.getMessage(), e);
            return false;
        }
    }

    public String getUserName(String token) {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(base64SecretBytes)
                .parseClaimsJws(token);
        return claims.getBody().get(NAME).toString();
    }

    public String getUserEmail(String token) {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(base64SecretBytes)
                .parseClaimsJws(token);
        return claims.getBody().get(ID).toString();
    }

    private void logClaims(Claims claimsBody,StringBuilder toLog) {
        String separator = System.getProperty("line.separator");
        toLog.append(separator);
        toLog.append("ID: " + claimsBody.getId() + separator);
        toLog.append("Name: " + claimsBody.get(NAME) + separator);
        toLog.append("Expiration: " + claimsBody.getExpiration() + separator);
        LOG.debug(toLog.toString());
    }

    public String resolveToken(final String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            return getUserName(token);
        }
        return null;
    }
}
