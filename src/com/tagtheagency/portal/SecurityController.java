package com.tagtheagency.portal;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/security")
public class SecurityController {

	//TODO move secret key into config file!
    private String secret = "TagYou'reIt";
    
	@PostMapping("") 
	public ResponseEntity<?> login(@RequestParam("token") String token) throws GeneralSecurityException, IOException {

		System.out.println("Handling google token "+token);
		
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
			.setAudience(Collections.singletonList("238046755257-ki0kkc04d49j1cr110n54dfakrgmtuh5.apps.googleusercontent.com"))
			.build();
		
		

		GoogleIdToken idToken = verifier.verify(token);
		if (idToken != null) {
			Payload payload = idToken.getPayload();

			String email = payload.getEmail();
			
			if ("tagtheagency.com".equals(payload.getHostedDomain())) {
				
				Date now = new Date();
		        Date expiration = new Date(now.getTime() + TimeUnit.HOURS.toMillis(1l));

		        Claims claims = Jwts.claims();
		        claims.put("name", payload.get("name"));
		        claims.setSubject(email)
		        .setIssuedAt(now)
                .setExpiration(expiration);
		        
		        String jwt = Jwts.builder()
		                .setId(UUID.randomUUID().toString())
		                .setClaims(claims)
		                .signWith(SignatureAlgorithm.HS512, secret)
		                .compact();
		        
		        return ResponseEntity.ok(Collections.singletonMap("jwt", jwt));
				
			}
			return ResponseEntity.badRequest().body(Collections.singletonMap("error", "You must login with your @tagtheagency.com account"));
			
		} else {			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid oauth token"));
		}

	}
	
	@PostMapping("jwt")
	public void test(@RequestParam("jwt") String token) {
        String username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        
        System.out.println(username);
	}
}
