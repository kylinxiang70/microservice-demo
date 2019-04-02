package org.kylin.authcenter.security;

import org.kylin.authcenter.constant.InfoConstant;
import org.kylin.authcenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(
                        MessageFormat.format(InfoConstant.USER_NAME_NOT_FOUND_1, s)));
    }
}
