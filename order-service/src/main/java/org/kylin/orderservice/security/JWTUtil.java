package org.kylin.orderservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.kylin.orderservice.exception.InvalidJwtAuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class JWTUtil {
    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; // 1h

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

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
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
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
