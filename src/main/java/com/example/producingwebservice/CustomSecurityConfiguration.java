package com.example.producingwebservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity(debug = true)
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

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

        http.authorizeRequests().anyRequest().authenticated()
                .and().x509()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .sessionAuthenticationFailureHandler(customAuthenticationFailureHandler() )
                .and().csrf().disable().logout();

        http.httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).sessionAuthenticationFailureHandler(customAuthenticationFailureHandler())
                .and().csrf().disable().logout();
    }

    private final CustomAuthenticationProvider ap;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.ap);
    }
}

