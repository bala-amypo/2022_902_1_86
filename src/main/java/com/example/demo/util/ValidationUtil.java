 2. CONFIG
config/SecurityConfig.java

________________________________________
config/SwaggerConfig.java

________________________________________
🔹 3. DTOs
dto/AuthRequest.java

dto/RegisterRequest.java
package com.example.demo.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
}
dto/FarmRequest.java
package com.example.demo.dto;

import lombok.Data;

@Data
public class FarmRequest {
    private Double soilPH;
    private Double waterLevel;
    private String season;
}
dto/CropRequest.java
package com.example.demo.dto;

import lombok.Data;

@Data
public class CropRequest {
    private String name;
}
dto/FertilizerRequest.java
package com.example.demo.dto;

import lombok.Data;

@Data
public class FertilizerRequest {
    private String cropName;
}
________________________________________
🔹 4. EXCEPTION
exception/BadRequestException.java
package com.example.demo.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
exception/ResourceNotFoundException.java
package com.example.demo.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
exception/GlobalExceptionHandler.java
package com.example.demo.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBad(BadRequestException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
________________________________________
🔹 5. SECURITY
security/CustomUserDetailsService.java
package com.example.demo.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        return User.withUsername(email)
                .password("{noop}password")
                .roles("USER")
                .build();
    }
}
security/JwtTokenProvider.java
package com.example.demo.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String SECRET = "secret";
    private final long EXP = 86400000;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXP))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
security/JwtAuthenticationFilter.java
package com.example.demo.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider provider;

    public JwtAuthenticationFilter(JwtTokenProvider provider) {
        this.provider = provider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        filterChain.doFilter(request, response);
    }
}
security/JwtAuthenticationEntryPoint.java
package com.example.demo.security;

import jakarta.servlet.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
________________________________________
🔹 6. UTIL
util/ValidationUtils.java
package com.example.demo.util;

public class ValidationUtils {

    public static boolean validSeason(String season) {
        return season.equalsIgnoreCase("Kharif")
            || season.equalsIgnoreCase("Rabi")
            || season.equalsIgnoreCase("Summer");
    }
}

