package com.Airline.Security;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.Airline.Model.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwTUtil {

	@Value("${jwt.secret}")
	private String jwtSecret;
	
	
	@Value("${jwt.expiration.ms}")
	private String jwtExpirationMs;
	
	private Key getSigningKey() {
//		System.out.println("Secret = " + jwtSecret);
	    byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
	    return Keys.hmacShaKeyFor(keyBytes);
	}

	public  String generateToken(Users user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(jwtExpirationMs)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)  // Use the key instance
                .compact();
	}
	
	public boolean validateToken(String token, UserDetails user) {
		 try {
	            Claims claims = extractClaims(token);
	            boolean isExpired = claims.getExpiration().before(new Date());
	             final String extractedName = extractUsername(token);
//	            if (isExpired) {
//	                return false;
//	            }
	            return (extractedName.equals(user.getUsername()) && ! isExpired);
	        } catch (JwtException | IllegalArgumentException e) {
	            return false;
	        }
	}
	public Claims extractClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String extractUsername(String token) {
	 return  extractClaims(token)
	  			.getSubject();
	}
}
