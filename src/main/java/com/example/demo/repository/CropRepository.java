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