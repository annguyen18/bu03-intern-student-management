package com.viettel.intern.config.security.jwt;

import com.viettel.intern.config.security.CustomUser;
import com.viettel.intern.constant.AppConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private static final String AUTHORITIES_KEY = "authorities";
    private static final String USER_ID_KEY = "userId";
    private static final String USERNAME_KEY = "username";
    private static final String EMAIL_KEY = "email";
    private static final String STATUS_KEY = "status";
    private final Logger log = LoggerFactory.getLogger(JwtUtil.class);
    private final Key key;

    private final JwtParser jwtParser;

    private static final String SECRET = "MjQ1YzRjOTk3YWYzZTQ3N2VlYWQ0ZTEzMDI4ZDBlMTNmZTg2OGFkY2ZkY2YyYjY2MDMxMWRmZGRmMTRkMjc0YWM1ODg3YTI3YmVjYTJiNTRlODE4NzEyZjg2NDY3MDMyMjFjZDk3YzFiNWNmMGZmNmNjNWZmYzM3N2FhNDI5ZmQ=";
    private static final long TOKEN_VALIDITY_IN_MILLISECONDS = 3600000L; // 1 hour
    private static final long TOKEN_VALIDITY_IN_MILLISECONDS_FOR_REMEMBER_ME = 86400000L; // 1 day

    public JwtUtil() {
        byte[] keyBytes;
        log.debug("Using a Base64-encoded JWT secret key");
        keyBytes = Decoders.BASE64.decode(SECRET);
        key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String createToken(Authentication authentication, Boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe != null && rememberMe) {
            validity = new Date(now + TOKEN_VALIDITY_IN_MILLISECONDS_FOR_REMEMBER_ME);
        } else {
            validity = new Date(now + TOKEN_VALIDITY_IN_MILLISECONDS);
        }
        Map<String, Object> claims = new HashMap<>();
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        claims.put(USERNAME_KEY, authentication.getName());
        claims.put(USER_ID_KEY, customUser.getUserId() + "");
        claims.put(EMAIL_KEY, customUser.getEmail() + "");
        claims.put(STATUS_KEY, customUser.getStatus() + "");
        claims.put(AUTHORITIES_KEY, authorities);
        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        Long userId = null;
        String email = null;
        Integer status = null;
        String username = null;
        if (claims.get(USERNAME_KEY) != null) {
            username = (String) claims.get(USERNAME_KEY);
        }
        if (claims.get(USER_ID_KEY) != null) {
            userId = Long.valueOf((String) claims.get(USER_ID_KEY));
        }
        if (claims.get(EMAIL_KEY) != null) {
            email = (String) claims.get(EMAIL_KEY);
        }
        if (claims.get(STATUS_KEY) != null) {
            status = Integer.parseInt((String) claims.get(STATUS_KEY));
        }

        CustomUser principal = new CustomUser(username, "", Objects.equals(AppConstant.ActiveStatus.ACTIVE, status),
                true, true, true, authorities, userId, email, status);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }
}
