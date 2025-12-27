package com.example.demo.repository;

import com.example.demo.entity.Fertilizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FertilizerRepository extends JpaRepository<Fertilizer, Long> {
    
    @Query("SELECT f FROM Fertilizer f WHERE LOWER(f.recommendedForCrops) LIKE LOWER(CONCAT('%', :cropName, '%'))")
    List<Fertilizer> findByCropName(@Param("cropName") String cropName);
}
