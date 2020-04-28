package com.app.ws.mobileappws.security;

import com.app.ws.mobileappws.data.UserEntity;
import com.app.ws.mobileappws.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private Environment environment;

    private UserRepository userRepository;

    public AuthorizationFilter(AuthenticationManager authenticationManager, Environment environment, UserRepository userRepository) {
        super(authenticationManager);
        this.environment = environment;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(environment.getProperty("authorizationFilter.token.header.name"));
        if(token == null) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(environment.getProperty("authorizationFilter.token.header.name"));
        String user = Jwts.parser()
                .setSigningKey(environment.getProperty("token.secret"))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        if(user != null) {
            UserEntity userEntity = userRepository.findUsersByEmail(user);
            UserPrincipal principal = new UserPrincipal(userEntity);
            return new UsernamePasswordAuthenticationToken(user, null, principal.getAuthorities());
        }
        return null;
    }
}

