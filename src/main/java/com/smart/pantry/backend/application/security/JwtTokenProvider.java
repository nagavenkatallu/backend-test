package com.smart.pantry.backend.application.security;

import com.smart.pantry.backend.application.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    // In a real application, this would be a secure, secret key stored in your application properties.
//    private final String jwtSecret = "venkatallu";
    @Value("${jwt.secret}")
    private String jwtSecret;

    // Token validity in milliseconds
    private final long jwtExpirationInMs = 86400000; // 24 hours
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(User user) {
        // This is a placeholder. In a real implementation, you would use a library like jjwt.
        // Example with jjwt:
         Date now = new Date();
         Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
         String jwtToken=Jwts.builder()
                 .setSubject(user.getEmail())
                 .setIssuedAt(new Date())
                 .setExpiration(expiryDate)
                 .signWith(getSigningKey())
                 .compact();
        System.out.println("Generated JWT for user: " + user.getEmail()+ " is: "+jwtToken);
        return jwtToken;

    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(jwt);
            return true;
        }
        catch (SignatureException ex) { // Use the more specific SignatureException
            System.out.println("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }

    public String getUserEmailFromJwt(String jwt) {
        Claims claims=Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(jwt).getBody();
        return claims.getSubject();
    }
}
