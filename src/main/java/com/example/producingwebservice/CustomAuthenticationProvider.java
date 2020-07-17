package com.example.producingwebservice;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    public static final List<SimpleGrantedAuthority> AUTHORITIES = Collections.singletonList(new SimpleGrantedAuthority("USER"));

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();

        String password = authentication.getCredentials().toString();

        if(username.equals("danilo") && password.equals("password")){
            return new UsernamePasswordAuthenticationToken("name", "pw",
                    AUTHORITIES);
        }
        else{
            throw new BadCredentialsException("couldn't Authenticate using username" + username);
        }

    }

    @Override
    public boolean supports(Class<?> auth) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(auth);
    }
}
