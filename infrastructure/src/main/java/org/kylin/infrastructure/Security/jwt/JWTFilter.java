package org.kylin.infrastructure.Security.jwt;

import io.jsonwebtoken.JwtException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JWTFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws
            ServletException, IOException {
        try {
            Optional<JWTAuthentication> authentication = JWTUtil.getJWTAuthentication(req);
            SecurityContextHolder.getContext().setAuthentication(authentication.orElse(null));
            filterChain.doFilter(req, res);
        } catch (JwtException e) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
}
