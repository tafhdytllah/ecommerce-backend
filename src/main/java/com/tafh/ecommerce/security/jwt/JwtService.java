package com.tafh.ecommerce.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final JwtProperties properties;

  /* ====================
    SIGNING KEY
   ======================*/
  private SecretKey signingKey() {
    return Keys.hmacShaKeyFor(
      properties.getSecret().getBytes(StandardCharsets.UTF_8)
    );
  }

  /* ====================
    GENERATE TOKEN
   ======================*/
  public String generateToken(String userId, String role) {
    Date now = new Date();
    Date expiry = new Date(now.getTime() + properties.getExpiration());

    return Jwts.builder()
      .subject(userId)
      .claim("role", role)
      .issuer(properties.getIssuer())
      .issuedAt(now)
      .expiration(expiry)
      .signWith(signingKey())
      .compact();
  }

  /* ====================
    EXTRACTION
   ======================*/
  public String extractUserId(String token) {
    return parseClaims(token).getSubject();
  }

  public String extractRole(String token) {
    return parseClaims(token).get("role", String.class);
  }

  /* ====================
    VALIDATION
   ======================*/
  public boolean isTokenValid(String token) {
    try {
      parseClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /* ====================
    CORE
   ======================*/
  public Claims parseClaims(String token) {
    return Jwts.parser()
      .verifyWith(signingKey())
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }
}