package com.goldee.websocket.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTService {
	private static final String SECRET_KEY = "294A404E635266556A576E5A7234753778214125442A472D4B6150645367566B";
	// // private static final Key SECRET_KEY = Jwts.SIG.HS256.key().build();
	// SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		Claims claims = extractAllClaims(token);

		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSignInKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
		// return
		// Jwts.parser().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
		// return Jwts.parser()
		// .setSigningKey(SECRET_KEY)
		// .build()
		// .parseClaimsJws(token)
		// .getBody();
	}

	// private SecretKey getSignInKey() {
	// return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));

	// }
	private SecretKey getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);

	}

	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	public String generateToken(
			Map<String, Object> extractClaims,
			UserDetails userDetails) {
		return Jwts
				.builder()
				.claims(extractClaims)
				.subject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() * 1000 * 60 * 24))
				.signWith(getSignInKey())
				.compact();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}
