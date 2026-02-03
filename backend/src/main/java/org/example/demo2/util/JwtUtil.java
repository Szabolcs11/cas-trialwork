package org.example.demo2.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET = "CHANGE_ME_TO_A_LONG_RANDOM_SECRET_KEY_32_CHARS_MIN";
    public static String generateToken(String email, boolean isAdmin) {

        return Jwts.builder()
                .setSubject(email)
                .claim("role", isAdmin ? "admin" : "user")
                .setIssuedAt(new Date())
                .setExpiration(
                        Date.from(Instant.now().plus(1, ChronoUnit.HOURS))
                )
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }
}
