
1. DemoApplication
src/main/java/com/example/demo/DemoApplication.java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
________________________________________
2. Entities
2.1 User
entity/User.java
package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 100)
    private String name;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Farm> farms;
}
2.2 Farm
entity/Farm.java
package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "farms")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @NotBlank
    private String name;

    @NotNull
    private Double soilPH;

    @NotNull
    private Double waterLevel;

    @NotBlank
    private String season;
}
2.3 Crop
entity/Crop.java
package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "crops")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Double suitablePHMin;

    @NotNull
    private Double suitablePHMax;

    @NotNull
    private Double requiredWater;

    @NotBlank
    private String season;
}
2.4 Fertilizer
entity/Fertilizer.java
package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "fertilizers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fertilizer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String npkRatio;

    @NotBlank
    @Column(length = 500)
    private String recommendedForCrops;
}
2.5 Suggestion
entity/Suggestion.java
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "suggestions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @Column(length = 1000)
    private String suggestedCrops;

    @Column(length = 1000)
    private String suggestedFertilizers;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
________________________________________
3. Repositories
repository/UserRepository.java
package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
repository/FarmRepository.java
package com.example.demo.repository;

import com.example.demo.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FarmRepository extends JpaRepository<Farm, Long> {
    List<Farm> findByOwnerId(Long ownerId);
}
repository/CropRepository.java
package com.example.demo.repository;

import com.example.demo.entity.Crop;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CropRepository extends JpaRepository<Crop, Long> {

    @Query("SELECT c FROM Crop c WHERE c.suitablePHMin <= :ph AND c.suitablePHMax >= :ph " +
           "AND c.requiredWater <= :water AND c.season = :season")
    List<Crop> findSuitableCrops(@Param("ph") Double ph,
                                 @Param("water") Double water,
                                 @Param("season") String season);
}
repository/FertilizerRepository.java
package com.example.demo.repository;

import com.example.demo.entity.Fertilizer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FertilizerRepository extends JpaRepository<Fertilizer, Long> {
    List<Fertilizer> findByRecommendedForCropsContaining(String cropName);
}
repository/SuggestionRepository.java
package com.example.demo.repository;

import com.example.demo.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    List<Suggestion> findByFarmId(Long farmId);
}
________________________________________
4. Exceptions
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

    @ExceptionHandler({BadRequestException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleBadRequest(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
________________________________________
5. Utility
util/ValidationUtil.java
package com.example.demo.util;

import java.util.Set;

public class ValidationUtil {

    private static final Set<String> SEASONS = Set.of("Kharif", "Rabi", "Summer");

    public static boolean validSeason(String season) {
        return SEASONS.contains(season);
    }
}

