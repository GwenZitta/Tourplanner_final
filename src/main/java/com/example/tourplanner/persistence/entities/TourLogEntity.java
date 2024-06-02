package com.example.tourplanner.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "log")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TourLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String datetime;
    private String comment;
    private String difficulty;
    private String distance;
    private String time;
    private String rating;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_TOUR")
    private TourEntity tour;
}