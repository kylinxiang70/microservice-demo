package org.kylin.authcenter.config;

import org.kylin.infrastructure.security.jwt.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // eg: ADMIN -> ROLE-ADMIN
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                // set UserDetailsService
                .userDetailsService(this.userDetailsService)
                // password encoder
                .passwordEncoder(passwordEncoder());
    }

    // load password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                // disable csrf
                .csrf().disable()
                // based on jwt, disable session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // login required no authority
                .antMatchers("/auth/login").permitAll()
                // create user required no authority
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                // get users by filter required ADMIN authority
                .antMatchers(HttpMethod.POST, "/users/filter").hasRole("ADMIN")
                // get all users required ADMIN authority
                .antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                // get user by id required USER authority
                .antMatchers(HttpMethod.GET, "/users/*").hasRole("USER")
                // delete user by id required ADMIN authority
                .antMatchers(HttpMethod.DELETE, "/users/*").hasRole("ADMIN")
                // check the auth of the rest of the request
                //.anyRequest().authenticated();
                .and().addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class);
        // disable cache
        httpSecurity.headers().cacheControl();
    }
}
