package com.example.tourplanner.api;

import com.example.tourplanner.persistence.entities.TourEntity;
import com.example.tourplanner.service.TourService;
import com.example.tourplanner.service.dtos.TourDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "tour")
public class TourApi {

    @Autowired
    private TourService tourService;

    @GetMapping
    public List<TourDto> getAllTours() { return tourService.getAllTours(); }
    @GetMapping("/getTourById/{id}")
    public List<TourDto> getTourById(@PathVariable Long id) {
        return tourService.getTourById(id);
    }
    @GetMapping("/getTourByName/{name}")
    public List<TourDto> getTourByName(@PathVariable String name) {
        return tourService.getTourByName(name);
    }

    @PostMapping
    public void insertNewTour(@RequestBody TourDto tour) { tourService.saveNewTour(tour); }

    @DeleteMapping("/deleteTour/{id}")
    public void deleteTourById(@PathVariable Long id) {
        tourService.deleteTour(id);
    }

    @PutMapping
    public TourDto updateTour(@RequestBody TourDto tour) {
        return tourService.updateTour(tour);
    }

}
