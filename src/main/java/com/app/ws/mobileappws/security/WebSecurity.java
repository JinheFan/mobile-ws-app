package com.app.ws.mobileappws.security;

import com.app.ws.mobileappws.repository.UserRepository;
import com.app.ws.mobileappws.service.UserSerive;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserSerive userSerive;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final Environment environment;

    private final UserRepository userRepository;

    public WebSecurity(UserSerive userSerive, BCryptPasswordEncoder bCryptPasswordEncoder, Environment environment, UserRepository userRepository) {
        this.userSerive = userSerive;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.environment = environment;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, environment.getProperty("sign_up.path")).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new AuthenticationFilter(authenticationManager(), environment, userRepository))
                .addFilter(new AuthorizationFilter(authenticationManager(), environment))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSerive).passwordEncoder(bCryptPasswordEncoder);
    }
}


