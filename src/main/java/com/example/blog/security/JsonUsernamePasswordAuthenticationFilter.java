package com.example.blog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.SecurityContextRepository;

import java.io.IOException;

public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public JsonUsernamePasswordAuthenticationFilter(
            SecurityContextRepository securityContextRepository,
            SessionAuthenticationStrategy sessionAuthenticationStrategy,
            AuthenticationManager authenticationManager
    ) {
        super();
        setSecurityContextRepository(securityContextRepository);
        setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);
        });
        setAuthenticationFailureHandler((request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        });
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        var objectMapper = new ObjectMapper();
        LoginRequest json;
        try {
            json = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            throw new AuthenticationServiceException("failed to read request as json", e);
        }
        var username = json.username != null ? json.username : "";
        var password = json.password != null ? json.password : "";
        var token = new UsernamePasswordAuthenticationToken(username, password);
        return super.getAuthenticationManager().authenticate(token);
    }

    private record LoginRequest(String username, String password) {
    }
}
