package com.example.producingwebservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity(debug = true)
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AccountUserDetailsService accountUserDetailsService ;

    @Autowired
    public CustomSecurityConfiguration(CustomAuthenticationProvider customAuthenticationProvider,
                                       AccountUserDetailsService accountUserDetailsService) {
        ap = customAuthenticationProvider;
        this.accountUserDetailsService = accountUserDetailsService;
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return this.accountUserDetailsService ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().csrf().disable().logout();
//        http.httpBasic()
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        http.authorizeRequests().anyRequest().authenticated()
        .and().x509()
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
        .and().csrf().disable().logout();
    }

    private final CustomAuthenticationProvider ap;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.ap);
    }
}

