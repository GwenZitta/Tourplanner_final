package com.example.tourplanner.persistence.repositories;

import com.example.tourplanner.persistence.entities.TourLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourLogRepository extends JpaRepository<TourLogEntity, Long> {
    @Query("select te from TourLogEntity te where te.id = ?1")
    TourLogEntity findByTourLogId(Long id);

    @Query("select te from TourLogEntity te where te.tour.id = ?1")
    List<TourLogEntity> findByTourLogTourId(Long id);
}
