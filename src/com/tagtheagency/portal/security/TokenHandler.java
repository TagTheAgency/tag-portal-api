package com.tagtheagency.portal.security;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public final class TokenHandler {
	//TODO move secret key into config file!
    private String secret = "TagYou'reIt";

    @Autowired
    private UserDetailsService userService;

    public TokenHandler() {
    	//this.secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public UserDetails parseUserFromToken(String token) throws ExpiredJwtException, MalformedJwtException {
    	System.out.println("Decoding token "+token);
        String username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userService.loadUserByUsername(username);
    }

//    public String createTokenForUser(UserDetails user) {
//    	return createTokenForUser(user.getUsername());
//    }
//    
//    public String createTokenForUser(String email) {
//    	Date now = new Date();
//        Date expiration = new Date(now.getTime() + TimeUnit.HOURS.toMillis(1l));
//        return Jwts.builder()
//                .setId(UUID.randomUUID().toString())
//                .setSubject(email)
//                .setIssuedAt(now)
//                .setExpiration(expiration)
//                .signWith(SignatureAlgorithm.HS512, secret)
//                .compact();
//    }
}