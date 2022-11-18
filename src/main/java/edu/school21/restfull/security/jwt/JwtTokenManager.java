package edu.school21.restfull.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.school21.restfull.security.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenManager {

	@Value("${edu.school21.restfull.jwt.secret}")
	private String secret;
	@Value("${edu.school21.restfull.jwt.expiration-time}")
	private Integer expirationTime;

	@Autowired
	private ObjectMapper objectMapper;

	public String generateToken(CustomUserDetails userDetails) {
		try {
			return Jwts.builder()
					.setSubject(objectMapper.writeValueAsString(userDetails))
					.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
					.signWith(SignatureAlgorithm.HS512, secret)
					.compact();
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error during JWT generation");
		}
	}

	@Nullable
	public CustomUserDetails parseToken(String token) {
		try {
			String userDetails = Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody()
					.getSubject();

			return objectMapper.readValue(userDetails, CustomUserDetails.class);
		} catch (Exception e) {
			return null;
		}
	}

}
