package com.md.doctor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/h2-console/**").permitAll() //do not use in production!
                        .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll())
                .csrf().disable();

        http.headers().frameOptions().sameOrigin();

    }
}