package com.example.tourplanner.service;

import com.example.tourplanner.persistence.entities.TourEntity;
import com.example.tourplanner.service.dtos.TourDto;

import java.util.List;

public interface TourService {

    Long saveNewTour(TourDto tourDto);
    List<TourDto> getAllTours();
    List<TourDto> getTourByName(String name);
    List<TourDto> getTourById(Long id);
    TourDto updateTour(TourDto tourDto);
    void deleteTour(Long id);


}
