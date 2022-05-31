package com.cpilosenlaces.disheap_backend.security;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.cpilosenlaces.disheap_backend.exception.UnauthorizeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    public static final int JWT_TOKEN_VALIDITY = 21600; // 6 hours in seconds of assigned time validated
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public static final long EXPIRATION_DATE = 3600000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Autowired
    private JwtUserDetailsController jwtUserDetailsController;

    @PostConstruct
    protected void init() {

    }

    public String createToken(UUID id, String email, String role) {

        Claims claims = Jwts.claims();
        claims.put("id", String.valueOf(id));
        claims.put("username", email);
        claims.put("auth", new SimpleGrantedAuthority("ROLE_" + role));
        Date now = new Date();
        Date validity = new Date(now.getTime() + EXPIRATION_DATE);

        return Jwts.builder().setClaims(claims).setSubject(null)
                .signWith(SECRET_KEY) // Secret key to the token
                .setIssuedAt(now) // Creation date
                .setExpiration(validity) // Set the expiration date
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = jwtUserDetailsController.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("username", String.class);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(HEADER_STRING);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token, HttpServletRequest httpServletRequest)
            throws UnauthorizeException {

        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (IllegalArgumentException e) {
            throw new UnauthorizeException("Expired or invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("JWT Token expired.", ex.getCause());
            throw new UnauthorizeException("JWT Token Expired");
        }
    }

}