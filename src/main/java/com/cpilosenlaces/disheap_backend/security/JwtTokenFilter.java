package com.cpilosenlaces.disheap_backend.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cpilosenlaces.disheap_backend.exception.BadRequestException;
import com.cpilosenlaces.disheap_backend.exception.UnauthorizeException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        String token = jwtTokenProvider.resolveToken(httpServletRequest);

        try {
            if (token != null && jwtTokenProvider.validateToken(token, httpServletRequest)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (UnauthorizeException | BadRequestException ex) {
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
