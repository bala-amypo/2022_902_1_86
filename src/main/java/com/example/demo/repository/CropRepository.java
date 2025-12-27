package com.example.demo.repository;

import com.example.demo.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
    
    @Query("SELECT c FROM Crop c WHERE c.suitablePHMin <= :ph AND c.suitablePHMax >= :ph AND c.season = :season")
    List<Crop> findSuitableCrops(@Param("ph") Double ph, @Param("season") String season);
    
    @Query("SELECT c FROM Crop c WHERE c.suitablePHMin <= :ph AND c.suitablePHMax >= :ph AND c.requiredWater <= :water AND c.season = :season")
    List<Crop> findSuitableCropsByPhWaterSeason(@Param("ph") Double ph, @Param("water") Double water, @Param("season") String season);
}
