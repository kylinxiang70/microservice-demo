package org.kylin.authcenter.service.impl;

import org.kylin.authcenter.dto.BasicAuthDto;
import org.kylin.authcenter.dto.TokenDto;
import org.kylin.authcenter.exception.UserOperationException;
import org.kylin.authcenter.repository.UserRepository;
import org.kylin.authcenter.security.jwt.JWTProvider;
import org.kylin.authcenter.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {


    private JWTProvider jwtProvider;

    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    @Autowired
    public TokenServiceImpl(JWTProvider jwtProvider, UserRepository userRepository, AuthenticationManager
            authenticationManager) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public TokenDto getToken(BasicAuthDto dto) {

        String username = dto.getUsername();

        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(username, dto.getPassword());

        authenticationManager.authenticate(upat);

        String token = jwtProvider.createToken(username, this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserOperationException("Username " + username + "not found")).getRoles());
        return new TokenDto(username, token);
    }
}
