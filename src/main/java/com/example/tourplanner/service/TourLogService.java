package com.example.tourplanner.service;

import com.example.tourplanner.service.dtos.TourLogDto;

import java.util.List;

public interface TourLogService {
    List<TourLogDto> getAllTourLogs();

    List<TourLogDto> getTourLogById(Long id);

    List<TourLogDto> getTourLogByTourId(Long id);

    Long saveNewTourLog(TourLogDto tourLog);

    void deleteTourLog(Long id);

    TourLogDto updateTourLog(TourLogDto tourLog);
}
