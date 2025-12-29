package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    public SecurityConfig(JwtAuthenticationFilter jwtFilter) { this.jwtFilter = jwtFilter; }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



user Controller
package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
dto
authreq
package com.example.demo.dto;
public class AuthRequest {
    private String email;
    private String password;
    public AuthRequest() {}
    public AuthRequest(String email, String password) { this.email = email; this.password = password; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
Auth response
package com.example.demo.dto;
public class AuthResponse {
    private String token;
    private Long userId;
    private String email;
    private String role;
    public AuthResponse() {}
    public AuthResponse(String token, Long userId, String email, String role) {
        this.token = token; this.userId = userId; this.email = email; this.role = role;
    }
    public String getToken() { return token; }
}
crop req
package com.example.demo.dto;

public class CropRequest {
    private String name;
    private Double suitablePHMin;
    private Double suitablePHMax;
    private Double requiredWater;
    private String season;

    public CropRequest() {}

    // Add this constructor to satisfy Test line 450
    public CropRequest(String name, Double suitablePHMin, Double suitablePHMax, Double requiredWater, String season) {
        this.name = name;
        this.suitablePHMin = suitablePHMin;
        this.suitablePHMax = suitablePHMax;
        this.requiredWater = requiredWater;
        this.season = season;
    }

    public String getName() { return name; }
    public Double getSuitablePHMin() { return suitablePHMin; }
    public Double getSuitablePHMax() { return suitablePHMax; }
    public Double getRequiredWater() { return requiredWater; }
    public String getSeason() { return season; }
}
Farmreq
package com.example.demo.dto;

public class FarmRequest {
    private String name;
    private Double soilPH;
    private Double waterLevel;
    private String season;

    public FarmRequest() {}
    public FarmRequest(String name, Double soilPH, Double waterLevel, String season) {
        this.name = name; this.soilPH = soilPH; this.waterLevel = waterLevel; this.season = season;
    }

    public String getName() { return name; }
    public Double getSoilPH() { return soilPH; }
    public Double getWaterLevel() { return waterLevel; }
    public String getSeason() { return season; }

    // Setters (Required for JSON mapping)
    public void setName(String name) { this.name = name; }
    public void setSoilPH(Double soilPH) { this.soilPH = soilPH; }
    public void setWaterLevel(Double waterLevel) { this.waterLevel = waterLevel; }
    public void setSeason(String season) { this.season = season; }
}
fertilizerreq
package com.example.demo.dto;

public class FertilizerRequest {
    private String name;
    private String npkRatio;
    private String recommendedForCrops;

    public FertilizerRequest() {}

    // Constructor that might be used by tests
    public FertilizerRequest(String name, String npkRatio, String recommendedForCrops) {
        this.name = name;
        this.npkRatio = npkRatio;
        this.recommendedForCrops = recommendedForCrops;
    }

    public String getName() { return name; }
    public String getNpkRatio() { return npkRatio; }
    public String getRecommendedForCrops() { return recommendedForCrops; }
    
    public void setName(String name) { this.name = name; }
    public void setNpkRatio(String npkRatio) { this.npkRatio = npkRatio; }
    public void setRecommendedForCrops(String recommendedForCrops) { this.recommendedForCrops = recommendedForCrops; }
}
registerreq
package com.example.demo.dto;
public class RegisterRequest {
    private String name; private String email; private String password;
    public RegisterRequest() {}
    public RegisterRequest(String n, String e, String p) { this.name = n; this.email = e; this.password = p; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
entity
farm
package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "farms")
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    private String name;
    private Double soilPH;
    private Double waterLevel;
    private String season;

    public Farm() {}

    public Farm(Long id, User owner, String name, Double soilPH, Double waterLevel, String season) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.soilPH = soilPH;
        this.waterLevel = waterLevel;
        this.season = season;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
    public String getName() { return name; }
    public Double getSoilPH() { return soilPH; }
    public Double getWaterLevel() { return waterLevel; }
    public String getSeason() { return season; }

    // Manual Builder for Test Cases
    public static FarmBuilder builder() {
        return new FarmBuilder();
    }

    public static class FarmBuilder {
        private Long id;
        private User owner;
        private String name;
        private Double soilPH;
        private Double waterLevel;
        private String season;

        public FarmBuilder id(Long id) { this.id = id; return this; }
        public FarmBuilder owner(User owner) { this.owner = owner; return this; }
        public FarmBuilder name(String name) { this.name = name; return this; }
        public FarmBuilder soilPH(Double soilPH) { this.soilPH = soilPH; return this; }
        public FarmBuilder waterLevel(Double waterLevel) { this.waterLevel = waterLevel; return this; }
        public FarmBuilder season(String season) { this.season = season; return this; }

        public Farm build() {
            return new Farm(id, owner, name, soilPH, waterLevel, season);
        }
    }
}
fertilizer
package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fertilizers")
public class Fertilizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String npkRatio;
    private String recommendedForCrops;

    public Fertilizer() {}

    public Fertilizer(Long id, String name, String npkRatio, String recommendedForCrops) {
        this.id = id;
        this.name = name;
        this.npkRatio = npkRatio;
        this.recommendedForCrops = recommendedForCrops;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getNpkRatio() { return npkRatio; }
    public String getRecommendedForCrops() { return recommendedForCrops; }

    // Manual Builder for Test Cases
    public static FertilizerBuilder builder() {
        return new FertilizerBuilder();
    }

    public static class FertilizerBuilder {
        private Long id;
        private String name;
        private String npkRatio;
        private String recommendedForCrops;

        public FertilizerBuilder id(Long id) { this.id = id; return this; }
        public FertilizerBuilder name(String name) { this.name = name; return this; }
        public FertilizerBuilder npkRatio(String npkRatio) { this.npkRatio = npkRatio; return this; }
        public FertilizerBuilder recommendedForCrops(String recommendedForCrops) { this.recommendedForCrops = recommendedForCrops; return this; }
        
        public Fertilizer build() {
            return new Fertilizer(id, name, npkRatio, recommendedForCrops);
        }
    }
}
suggestion
package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "suggestions")
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;

    private String suggestedCrops;
    private String suggestedFertilizers;
    private LocalDateTime createdAt;

    public Suggestion() {}
    
    // Updated constructor to match the builder
    public Suggestion(Long id, Farm farm, String suggestedCrops, String suggestedFertilizers, LocalDateTime createdAt) {
        this.id = id;
        this.farm = farm;
        this.suggestedCrops = suggestedCrops;
        this.suggestedFertilizers = suggestedFertilizers;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() { this.createdAt = LocalDateTime.now(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSuggestedCrops() { return suggestedCrops; }
    public String getSuggestedFertilizers() { return suggestedFertilizers; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public static SuggestionBuilder builder() { return new SuggestionBuilder(); }
    public static class SuggestionBuilder {
        private Long id; private Farm farm; private String suggestedCrops; private String suggestedFertilizers; private LocalDateTime createdAt;
        public SuggestionBuilder id(Long id) { this.id = id; return this; }
        public SuggestionBuilder farm(Farm farm) { this.farm = farm; return this; }
        public SuggestionBuilder suggestedCrops(String c) { this.suggestedCrops = c; return this; }
        public SuggestionBuilder suggestedFertilizers(String f) { this.suggestedFertilizers = f; return this; }
        public SuggestionBuilder createdAt(LocalDateTime ct) { this.createdAt = ct; return this; }
        public Suggestion build() { return new Suggestion(id, farm, suggestedCrops, suggestedFertilizers, createdAt); }
    }
}
user
package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;

    public User() {}
    public User(Long id, String name, String email, String password, String role) {
        this.id = id; this.name = name; this.email = email; this.password = password; this.role = role;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public static UserBuilder builder() { return new UserBuilder(); }
    public static class UserBuilder {
        private Long id; private String name; private String email; private String password; private String role;
        public UserBuilder id(Long id) { this.id = id; return this; }
        public UserBuilder name(String name) { this.name = name; return this; }
        public UserBuilder email(String email) { this.email = email; return this; }
        public UserBuilder password(String password) { this.password = password; return this; }
        public UserBuilder role(String role) { this.role = role; return this; }
        public User build() { return new User(id, name, email, password, role); }
    }
}
exception
global
package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(BadRequestException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArg(IllegalArgumentException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }
}
badreq
package com.example.demo.exception;
public class BadRequestException extends RuntimeException { public BadRequestException(String msg) { super(msg); } }
resourcenotfound
package com.example.demo.exception;
public class ResourceNotFoundException extends RuntimeException { public ResourceNotFoundException(String msg) { super(msg); } }

repo
crop
package com.example.demo.repository;
import com.example.demo.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CropRepository extends JpaRepository<Crop, Long> {
    @Query("SELECT c FROM Crop c WHERE :ph BETWEEN c.suitablePHMin AND c.suitablePHMax AND c.season = :season")
    List<Crop> findSuitableCrops(Double ph, String season);
}
farm
package com.example.demo.repository;
import com.example.demo.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FarmRepository extends JpaRepository<Farm, Long> {
    List<Farm> findByOwnerId(Long ownerId);
}
fertilizer
package com.example.demo.repository;
import com.example.demo.entity.Fertilizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface FertilizerRepository extends JpaRepository<Fertilizer, Long> {
    @Query("SELECT f FROM Fertilizer f WHERE f.recommendedForCrops LIKE %:cropName%")
    List<Fertilizer> findByCropName(String cropName);
}
suggestion
package com.example.demo.repository;
import com.example.demo.entity.Fertilizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface FertilizerRepository extends JpaRepository<Fertilizer, Long> {
    @Query("SELECT f FROM Fertilizer f WHERE f.recommendedForCrops LIKE %:cropName%")
    List<Fertilizer> findByCropName(String cropName);
}
user
package com.example.demo.repository;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
security
authfilter
package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider tokenProvider;
    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) { this.tokenProvider = tokenProvider; }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (tokenProvider.validateToken(token)) {
                String role = tokenProvider.getRole(token);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        tokenProvider.getUserId(token), null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}
tokenprovider
package com.example.demo.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenProvider {
    // This key is long enough to satisfy the 256-bit requirement
    private String secret = "SecretKeyForJWTTokenGenerationThatIsAtLeast32CharactersLong12345";

    public String createToken(Long userId, String email, String role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("userId", userId);
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmail(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public Long getUserId(String token) {
        Object id = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("userId");
        return Long.valueOf(id.toString());
    }

    public String getRole(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("role").toString();
    }
}
service
catalog
package com.example.demo.service;
import com.example.demo.entity.*;
import java.util.List;

public interface CatalogService {
    Crop addCrop(Crop crop);
    Fertilizer addFertilizer(Fertilizer fertilizer);
    List<Crop> findSuitableCrops(Double ph, Double water, String season);
    List<Fertilizer> findFertilizersForCrops(List<String> cropNames);
}
crop
package com.example.demo.service;

import com.example.demo.entity.Crop;

import java.util.List;

public interface CropService {

    Crop saveCrop(Crop crop);

    List<Crop> getAllCrops();

    Crop getCropById(Long id);

    void deleteCrop(Long id);
}

farm
package com.example.demo.service;

import com.example.demo.entity.Farm;
import java.util.List;

public interface FarmService {
    Farm createFarm(Farm farm, Long ownerId);
    List<Farm> getFarmsByOwner(Long ownerId);
    Farm getFarmById(Long farmId);
}
fertilizer
package com.example.demo.service;

import com.example.demo.entity.Fertilizer;

import java.util.List;

public interface FertilizerService {

    Fertilizer addFertilizer(Fertilizer fertilizer);

    List<Fertilizer> getAllFertilizers();

    Fertilizer getFertilizerById(Long id);
}
suggestion
package com.example.demo.service;

import com.example.demo.entity.Suggestion;
import java.util.List;

public interface SuggestionService {
    Suggestion generateSuggestion(Long farmId);
    Suggestion getSuggestion(Long suggestionId);
    List<Suggestion> getSuggestionsByFarm(Long farmId);
}
user
package com.example.demo.service;
import com.example.demo.entity.User;
import java.util.List;

public interface UserService {
    User register(User user);
    User findByEmail(String email);
    User findById(Long id);
    // Added to fix UserController errors
    User create(User user);
    User getById(Long id);
    List<User> getAll();
    User update(Long id, User user);
    void delete(Long id);
}
catalogimpl
package com.example.demo.service;

import com.example.demo.entity.Crop;
import com.example.demo.entity.Fertilizer;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.CropRepository;
import com.example.demo.repository.FertilizerRepository;
import com.example.demo.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final CropRepository cropRepo;
    private final FertilizerRepository fertRepo;

    // Constructor Injection
    public CatalogServiceImpl(CropRepository cropRepo, FertilizerRepository fertRepo) {
        this.cropRepo = cropRepo;
        this.fertRepo = fertRepo;
    }

    @Override
    public Crop addCrop(Crop crop) {
        // Test 40: PH Range validation
        if (crop.getSuitablePHMin() == null || crop.getSuitablePHMax() == null || 
            crop.getSuitablePHMin() > crop.getSuitablePHMax()) {
            throw new BadRequestException("PH min must be <= PH max");
        }

        // Test 47: Season validation
        if (!ValidationUtil.validSeason(crop.getSeason())) {
            throw new BadRequestException("Invalid season");
        }

        return cropRepo.save(crop);
    }

    @Override
    public Fertilizer addFertilizer(Fertilizer fertilizer) {
        // Test 41: NPK Ratio validation (Regex for number-number-number)
        if (fertilizer.getNpkRatio() == null || !fertilizer.getNpkRatio().matches("\\d+-\\d+-\\d+")) {
            throw new BadRequestException("Invalid NPK format");
        }

        return fertRepo.save(fertilizer);
    }

    @Override
    public List<Crop> findSuitableCrops(Double ph, Double waterLevel, String season) {
        // Based on AllFunctionalTests requirements, repo call uses ph and season
        return cropRepo.findSuitableCrops(ph, season);
    }

    @Override
    public List<Fertilizer> findFertilizersForCrops(List<String> cropNames) {
        // Test 28: Aggregate unique fertilizers for multiple crops
        Set<Fertilizer> uniqueFertilizers = new HashSet<>();
        
        if (cropNames != null) {
            for (String name : cropNames) {
                List<Fertilizer> found = fertRepo.findByCropName(name);
                if (found != null) {
                    uniqueFertilizers.addAll(found);
                }
            }
        }
        
        return new ArrayList<>(uniqueFertilizers);
    }
}
farmimpl
package com.example.demo.service;

import com.example.demo.entity.Farm;
import com.example.demo.entity.User;
import com.example.demo.repository.FarmRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FarmServiceImpl implements FarmService {
    private final FarmRepository farmRepo;
    private final UserRepository userRepo;

    // Constructor signature required by Test line 357
    public FarmServiceImpl(FarmRepository farmRepo, UserRepository userRepo) {
        this.farmRepo = farmRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Farm createFarm(Farm farm, Long ownerId) {
        if (farm.getSoilPH() < 3 || farm.getSoilPH() > 10) throw new IllegalArgumentException("Invalid pH level");
        return farmRepo.save(farm);
    }
    @Override public List<Farm> getFarmsByOwner(Long id) { return farmRepo.findByOwnerId(id); }
    @Override public Farm getFarmById(Long id) { return farmRepo.findById(id).orElseThrow(); }
}
suggestionimpl
package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.SuggestionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuggestionServiceImpl implements SuggestionService {
    private final FarmService farmService;
    private final CatalogService catalogService;
    private final SuggestionRepository repo;

    public SuggestionServiceImpl(FarmService farmService, CatalogService catalogService, SuggestionRepository repo) {
        this.farmService = farmService;
        this.catalogService = catalogService;
        this.repo = repo;
    }

    @Override
    public Suggestion generateSuggestion(Long farmId) {
        Farm farm = farmService.getFarmById(farmId);
        List<Crop> crops = catalogService.findSuitableCrops(farm.getSoilPH(), farm.getWaterLevel(), farm.getSeason());
        List<String> cropNames = crops.stream().map(Crop::getName).collect(Collectors.toList());
        List<Fertilizer> ferts = catalogService.findFertilizersForCrops(cropNames);
        
        Suggestion s = Suggestion.builder()
                .farm(farm)
                .suggestedCrops(String.join(",", cropNames))
                .suggestedFertilizers(ferts.stream().map(Fertilizer::getName).collect(Collectors.joining(",")))
                .build();
        return repo.save(s);
    }

    @Override
    public Suggestion getSuggestion(Long id) {
        return repo.findById(id).orElseThrow(() -> new com.example.demo.exception.ResourceNotFoundException("Not found"));
    }

    @Override
    public List<Suggestion> getSuggestionsByFarm(Long id) {
        return repo.findByFarmId(id);
    }
}
userImpl
package com.example.demo.service;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) throw new BadRequestException("Email already exists");
        if (user.getRole() == null) user.setRole("USER");
        if (user.getPassword() != null) user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override public User findByEmail(String email) { return userRepository.findByEmail(email).orElse(null); }
    @Override public User findById(Long id) { return userRepository.findById(id).orElseThrow(() -> new BadRequestException("User missing")); }
    @Override public User create(User user) { return register(user); }
    @Override public User getById(Long id) { return findById(id); }
    @Override public List<User> getAll() { return userRepository.findAll(); }
    @Override public User update(Long id, User user) { user.setId(id); return userRepository.save(user); }
    @Override public void delete(Long id) { userRepository.deleteById(id); }
}
validation
package com.example.demo.util;
public class ValidationUtil {
    public static boolean validSeason(String season) {
        if (season == null) return false;
        return season.equalsIgnoreCase("Kharif") || 
               season.equalsIgnoreCase("Rabi") || 
               season.equalsIgnoreCase("Summer");
    }
}
demo
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
application properties
spring.application.name=demo
# Don't change the port
server.port = 9001
# for https
server.forward-headers-strategy=framework 


#Database Connectivity
#mysql Connectivity

spring.datasource.url=jdbc:mysql://localhost:3306/sample?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=Amypo
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

#Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
server.forward-headers-strategy=framework
# Allow H2 Console or Swagger if needed
spring.mvc.pathmatch.matching-strategy=ant_path_matcher
pom.xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.3.4</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.example</groupId>
	<artifactId>demo</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>demo</name>
	<description>Demo project for Spring Boot</description>
	<url/>
	<licenses>
		<license/>
	</licenses>
	<developers>
		<developer/>
	</developers>
	<scm>
		<connection/>
		<developerConnection/>
		<tag/>
		<url/>
	</scm>
	<properties>
		<java.version>17</java.version>
	</properties>
	<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
		<!-- https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.0.33</version>
</dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
            <scope>provided</scope>
        </dependency>
		<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
		<dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.3.0</version>
        </dependency>
		<!-- JWT library -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>

        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>7.8.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-surefire-plugin</artifactId>
				<version>3.2.5</version>
				<dependencies>
					<dependency>
						<groupId>org.apache.maven.surefire</groupId>
						<artifactId>surefire-testng</artifactId>
						<version>3.2.5</version>
					</dependency>
					<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>

				</dependencies>
			</plugin>
		</plugins>
	</build>


</project>