package com.example.tourplanner.service.dtos;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TourDto implements Serializable{
    private Long id;
    private String name;
    private String description;
    private String from_location;
    private String to_location;
    private String transportType;
    private String distance;
    private String duration;
}
