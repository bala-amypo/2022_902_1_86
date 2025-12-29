package com.example.demo.controller;
import com.example.demo.dto.*;
import com.example.demo.entity.User;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest rr) {
        User u = userService.register(User.builder().name(rr.getName()).email(rr.getEmail()).password(rr.getPassword()).build());
        return ResponseEntity.ok(u);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest ar) {
        User u = userService.findByEmail(ar.getEmail());
        if(u != null && passwordEncoder.matches(ar.getPassword(), u.getPassword())) {
            String token = jwtTokenProvider.createToken(u.getId(), u.getEmail(), u.getRole());
            return ResponseEntity.ok(new AuthResponse(token, u.getId(), u.getEmail(), u.getRole()));
        }
        return ResponseEntity.status(401).build();
    }
}