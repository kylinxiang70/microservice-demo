package org.kylin.authcenter.service;


import org.kylin.authcenter.dto.BasicAuthDto;
import org.kylin.authcenter.dto.TokenDto;

public interface TokenService {
    TokenDto getToken(BasicAuthDto dto);
}
