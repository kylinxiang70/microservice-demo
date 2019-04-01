package org.kylin.authcenter.controller;

import org.kylin.authcenter.dto.BasicAuthDto;
import org.kylin.authcenter.dto.TokenDto;
import org.kylin.authcenter.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> getToken(@RequestBody BasicAuthDto dto) {
        return ok(tokenService.getToken(dto));
    }
}
