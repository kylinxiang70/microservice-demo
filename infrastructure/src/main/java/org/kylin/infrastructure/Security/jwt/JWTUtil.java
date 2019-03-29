package org.kylin.infrastructure.Security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.kylin.infrastructure.exception.InvalidJwtAuthenticationException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class JWTUtil {
    private static String secretKey = Base64.getEncoder().encodeToString("secret".getBytes());

    public Optional<JWTAuthentication> getJWTAuthentication(ServletRequest req) {
        String token = resolveToken((HttpServletRequest) req);
        if (token != null && validateToken(token)) {
            if (checkRole(token)) {
                return Optional.of(new JWTAuthentication(true, getRoles(token), getUsername(token)));
            }
        }
        return Optional.empty();
    }

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject();
    }

    private String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    private boolean checkRole(String resolvedToken) {
        List<String> roles = getRoles(resolvedToken);
        return roles.stream().anyMatch(x -> x.equals("ROLE_USER"));
    }

    private List<String> getRoles(String resolvedToken) {
        Jws<Claims> claims = getClaims(resolvedToken);
        return (List<String>) claims.getBody().get("roles", List.class);
    }

    private boolean validateToken(String token) {
        try {
            Jws<Claims> claims = getClaims(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }

    private Jws<Claims> getClaims(String resolvedToken) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(resolvedToken);
    }
}

