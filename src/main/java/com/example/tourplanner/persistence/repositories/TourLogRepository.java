package com.example.tourplanner.persistence.repositories;

import com.example.tourplanner.persistence.entities.TourLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourLogRepository extends JpaRepository<TourLogEntity, Long> {
}
