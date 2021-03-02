package pet.authSecurityPosgreSQL.config.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Slf4j
public class TokenProvider {
    private String secret = "jwt-auth-app";

    private final int validityTime = 3600000;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected void init() {
        secret = java.util.Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        Date currentDate = new Date();
        Date validity = new Date(currentDate.getTime() + validityTime);
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        log.info("Token created for user with username {}", username);
        return token;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) throws IllegalArgumentException{
        String bearerToken = req.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            log.info("Token is valid");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Jwt token is inspired or invalid");
            return false;
        }
    }
}
