package com.tafh.ecommerce.security.jwt;

import io.jsonwebtoken.Claims;

public final class UserPrincipalMapper {

  private UserPrincipalMapper() {}

  public static UserPrincipal fromClaims(Claims claims) {
    return new UserPrincipal(
      claims.getSubject(),                  // userid
      claims.get("role", String.class),  // role
      true                                  // active (trust token)
    );
  }
}
