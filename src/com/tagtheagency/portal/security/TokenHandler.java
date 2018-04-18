package com.tagtheagency.portal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;

@Service
@PropertySource(value= {"classpath:application.properties"})

public final class TokenHandler {
	@Value("${pitch.jwtsecret}")
    private String secret = "";

    @Autowired
    private UserDetailsService userService;

    public TokenHandler() {
    }

    public UserDetails parseUserFromToken(String token) throws ExpiredJwtException, MalformedJwtException {
        String username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userService.loadUserByUsername(username);
    }


}