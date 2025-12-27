package com.example.demo.repository;

import com.example.demo.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {
    List<Farm> findByOwnerId(Long ownerId);
    Optional<Farm> findByIdAndOwnerId(Long farmId, Long ownerId);
}