package com.example.tourplanner.persistence.repositories;

import com.example.tourplanner.persistence.entities.TourEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TourRepository extends JpaRepository<TourEntity, Long> {
 @Query("select te from TourEntity te where te.id = ?1")
  public TourEntity findByTourId(Long id);
  
   @Query("select te from TourEntity te where te.name LIKE %?1%")
  public List<TourEntity> findByTourName(String name);
}
