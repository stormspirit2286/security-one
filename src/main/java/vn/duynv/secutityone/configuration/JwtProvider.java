package vn.duynv.secutityone.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import vn.duynv.secutityone.modal.CustomUserDetails;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {
    static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());

    private static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 3600 * 1000;

    public String generateToken(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = populateAuthorities(authorities);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 3600 * 1000))
                .claim("authorities", roles)
                .claim("email", userDetails.getEmail())
                .claim("username", userDetails.getUsername())
                .claim("userId", userDetails.getUserId())
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .claim("email", authentication.getName())
                .claim("type", "REFRESH")
                .signWith(key)
                .compact();
    }

    public String getEmailFromToken(String token) {
        String jwt = token.substring(7);
        Claims claims = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        return String.valueOf(claims.get("email"));
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> roles = new HashSet<>();
        for (GrantedAuthority grantedAuthority : authorities) {
            roles.add(grantedAuthority.getAuthority());
        }
        return String.join(",", roles);
    }
}
