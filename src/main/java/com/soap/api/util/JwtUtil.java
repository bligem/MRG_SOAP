package com.soap.api.util;

import com.soap.api.dto.Role;
import com.soap.api.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final Key key;
    private final long expiresInSeconds;

    public JwtUtil(@Value("${app.jwt.secret:change_this_in_prod}") String secret,
                   @Value("${app.jwt.expires-in:3600}") long expiresInSeconds) {
        this.key = Keys.hmacShaKeyFor(adjustSecret(secret).getBytes());
        this.expiresInSeconds = expiresInSeconds;
    }

    private String adjustSecret(String secret) {
        if (secret == null) secret = "change_this_in_prod";
        if (secret.length() < 32) {
            StringBuilder sb = new StringBuilder(secret);
            while (sb.length() < 32) sb.append("0");
            return sb.toString();
        }
        return secret;
    }

    /**
     * Generates JWT. Roles are stored as strings (role names) in the token claims.
     */
    public String generateToken(UUID userId, List<Role> roles) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiresInSeconds * 1000L);

        List<String> roleStrings = roles == null ? List.of() :
                roles.stream().map(Role::name).collect(Collectors.toList());

        JwtBuilder jb = Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(exp)
                .claim("roles", roleStrings)
                .signWith(key, SignatureAlgorithm.HS256);

        return jb.compact();
    }

    public Jws<Claims> parseToken(String token) throws JwtException {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public UUID getUserIdFromClaims(Claims claims) {
        String sub = claims.getSubject();
        return UUID.fromString(sub);
    }

    /**
     * Convert roles claim (list of strings) back to List<Role>.
     * Throws ResponseStatusException(UNAUTHORIZED) on invalid role string.
     */
    public List<Role> getRolesFromClaimsAsRoles(Claims claims) {
        Object r = claims.get("roles");
        if (r instanceof List<?>) {
            try {
                return ((List<?>) r).stream()
                        .filter(Objects::nonNull)
                        .map(Object::toString)
                        .map(Role::fromString)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException ex) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid role in token");
            }
        }
        return List.of();
    }


    /**
     * Parse the Authorization header ("Bearer ...") and return TokenDto(userId, roles-as-Role).
     * Returns 401 for malformed/expired/invalid tokens.
     */
    public TokenDto parseAuthHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization header missing or malformed");
        }
        String token = authHeader.substring("Bearer ".length()).trim();
        try {
            Jws<Claims> jws = parseToken(token);
            Claims claims = jws.getBody();
            UUID callerId = getUserIdFromClaims(claims);
            List<Role> callerRoles = getRolesFromClaimsAsRoles(claims);

            return new TokenDto(callerId, callerRoles);

        } catch (JwtException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token claims");
        }
    }
}
