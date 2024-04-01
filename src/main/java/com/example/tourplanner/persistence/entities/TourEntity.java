package com.example.tourplanner.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tour")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private String from_location;
    private String to_location;
    private String transportType;
    private String distance;
    private String timeEst;
    private String image;
}
