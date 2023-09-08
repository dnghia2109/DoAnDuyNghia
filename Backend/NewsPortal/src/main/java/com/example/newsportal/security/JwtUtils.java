package com.example.newsportal.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtils {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    // Tạo token từ thông tin của user
    public String generateToken(UserDetails userDetails) {

        // có thể thêm nd vào claim tùy ý
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("authorities", userDetails.getAuthorities());
//        extraClaims.put("email", userDetails.getUsername());
//        extraClaims.put("password", userDetails.getPassword());

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Lấy danh sách claims từ trong token để lấy đc thông tin user
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Lấy username(email) của user từ trong token
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        //claims.get("email");
        return claims.getSubject();
    }

    // Lấy ngày hết hạn của token
    private Date extractExpiration(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    // Kiểm tra xem token đã hết hạn hay chưa
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Kiểm tra token có hợp lệ k (trùng username + chưa hết hạn)
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return userDetails.getUsername().equals(extractUsername(token)) && !isTokenExpired(token);
    }

    // Build secret key jwt
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getTokenFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "JWT_COOKIE");
        if(cookie != null){
            return cookie.getValue();
        }
        return null;
    }


    // TODO: refresh token
//    public String refreshToken(String token ) {
//        final Date createdDate = new Date();
//        final Date expirationDate = extractExpiration(String.valueOf(new Date(System.currentTimeMillis() + jwtExpiration)));
//        final Claims claims = extractAllClaims(token);
//        claims.setIssuedAt(new Date(System.currentTimeMillis()));
//        claims.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration));
//
//        return Jwts.builder().setClaims(claims).signWith(getSignInKey()).compact();
//    }
//
//    public Boolean canRefreshToken(String token) {
//
//
//        return true;
//    }
}
