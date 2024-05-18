package com.example.tourplanner.api;

import com.example.tourplanner.persistence.entities.TourLogEntity;
import com.example.tourplanner.service.TourLogService;
import com.example.tourplanner.service.dtos.TourLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "tourlog")
public class TourLogApi {

    @Autowired
    private TourLogService tourLogService;

    @GetMapping
    public List<TourLogDto> getAllTourLogs() { return tourLogService.getAllTourLogs(); }
    @GetMapping("/getTourLogById/{id}")
    public List<TourLogDto> getTourLogById(@PathVariable Long id) {
        return tourLogService.getTourLogById(id);
    }
    @GetMapping("/getTourLogByTourId/{id}")
    public List<TourLogDto> getTourLogByTourId(@PathVariable Long id) {
        return tourLogService.getTourLogByTourId(id);
    }

    @PostMapping
    public Long insertNewTourLog(@RequestBody TourLogDto tourLog) { return tourLogService.saveNewTourLog(tourLog); }

    @DeleteMapping("/deleteTourLog/{id}")
    public void deleteTourLogById(@PathVariable Long id) {
        tourLogService.deleteTourLog(id);
    }

    @PutMapping
    public TourLogDto updateTourLog(@RequestBody TourLogDto tourLog) { return tourLogService.updateTourLog(tourLog); }

}
