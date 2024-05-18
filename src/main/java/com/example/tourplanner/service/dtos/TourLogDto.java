package com.example.tourplanner.service.dtos;

import com.example.tourplanner.persistence.entities.TourEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TourLogDto {
    private Long id;
    private TourEntity tour;
    private String datetime;
    private String comment;
    private String difficulty;
    private String distance;
    private String time;
    private String rating;
}
