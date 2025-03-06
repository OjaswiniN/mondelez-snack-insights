/* (C) 2023 Mondelez Insights */
package com.mondelez.config;

import com.mondelez.service.model.LocalUser;
import com.mondelez.service.model.User;
import io.jsonwebtoken.*;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

  private final AppProperties appProperties;

  public String generateAccessToken(User user) {
    return Jwts.builder()
        .setSubject(user.getId().toString())
        .setIssuer(appProperties.getAuth().getTokenIssuer())
        .setIssuedAt(new Date())
        .setExpiration(new Date(appProperties.getAuth().getTokenExpirationMS())) // 1 week
        .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
        .compact();
  }

  public String generateOAuth2Token(Authentication authentication) {
    LocalUser userPrincipal = (LocalUser) authentication.getPrincipal();
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMS());
    return Jwts.builder()
        .setSubject(userPrincipal.getUser().getId().toString())
        .setIssuedAt(new Date())
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
        .compact();
  }

  public UUID getUserId(String token) {
    Claims claims =
        Jwts.parser()
            .setSigningKey(appProperties.getAuth().getTokenSecret())
            .parseClaimsJws(token)
            .getBody();
    return UUID.fromString(claims.getSubject());
  }

  public String getUsername(String token) {
    Claims claims =
        Jwts.parser()
            .setSigningKey(appProperties.getAuth().getTokenSecret())
            .parseClaimsJws(token)
            .getBody();
    return claims.getSubject();
  }

  public Date getExpirationDate(String token) {
    Claims claims =
        Jwts.parser()
            .setSigningKey(appProperties.getAuth().getTokenSecret())
            .parseClaimsJws(token)
            .getBody();

    return claims.getExpiration();
  }

  public boolean validate(String token) {
    try {
      Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(token);
      return true;
    } catch (SignatureException ex) {
      log.error("Invalid JWT signature - {}", ex.getMessage());
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT token - {}", ex.getMessage());
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token - {}", ex.getMessage());
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT token - {}", ex.getMessage());
    } catch (IllegalArgumentException ex) {
      log.error("JWT claims string is empty - {}", ex.getMessage());
    }
    return false;
  }
}
