package com.example.tourplanner.service.impl;

import com.example.tourplanner.api.MapApi;
import com.example.tourplanner.persistence.entities.TourEntity;
import com.example.tourplanner.persistence.repositories.TourRepository;
import com.example.tourplanner.service.TourService;
import com.example.tourplanner.service.dtos.TourDto;
import com.example.tourplanner.service.mapper.TourMapper;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TourServiceImpl implements TourService {

    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private TourMapper tourMapper;
    @Autowired
    private MapApi mapApi;

    @Override
    public Long saveNewTour(TourDto tourDto) {
        String fromCoordinate = mapApi.searchAddress(tourDto.getFrom_location());
        String toCoordinate = mapApi.searchAddress(tourDto.getTo_location());
        Long[] result = mapApi.searchDirection(fromCoordinate, toCoordinate, tourDto.getTransportType());
        Long distance = result[0] / 1000;
        Long duration = result[1] / 3600;
        TourEntity te = new TourEntity(Long.MIN_VALUE, tourDto.getName(), tourDto.getDescription(), tourDto.getFrom_location(), tourDto.getTo_location(), tourDto.getTransportType(), (distance.toString() + "km"), (duration.toString() + "h"), "");
        TourEntity saved=tourRepository.save(te);
        return saved.getId();
    }

    @Override
    public List<TourDto> getAllTours() {
        System.out.println("handle get all tour request");
        List<TourDto> list_tours = new ArrayList<>();
        List<TourEntity> list_tourstour = tourRepository.findAll();
        for (TourEntity te : list_tourstour) {
            list_tours.add(new TourDto(te.getId(), te.getName(), te.getDescription(), te.getFrom_location(), te.getTo_location(), te.getTransportType(), te.getDistance(), te.getTimeEst()));
        }
        return list_tours;
    }

    @Override
    public List<TourDto> getTourByName(String name) {
        List<TourDto> list_tours = new ArrayList<>();
        List<TourEntity> list_tourstour = tourRepository.findByTourName(name);
        for (TourEntity te : list_tourstour) {
            list_tours.add(new TourDto(te.getId(), te.getName(), te.getDescription(), te.getFrom_location(), te.getTo_location(), te.getTransportType(), te.getDistance(), te.getTimeEst()));
        }
        return list_tours;
    }

    @Override
    public List<TourDto> getTourById(Long id) {
        List<TourDto> list_tours = new ArrayList<>();
        TourEntity tour_entity = tourRepository.findByTourId(id);
        list_tours.add(new TourDto(tour_entity.getId(), tour_entity.getName(), tour_entity.getDescription(), tour_entity.getFrom_location(), tour_entity.getTo_location(), tour_entity.getTransportType(), tour_entity.getDistance(), tour_entity.getTimeEst()));
        return list_tours;

    }

    @Override
    public TourDto updateTour(TourDto tourDto) {
        TourEntity foundedTour = tourRepository.findByTourId(tourDto.getId());
        if (foundedTour != null) {
            if (tourDto.getName() != null) {
                foundedTour.setName(tourDto.getName());
            }
            if (tourDto.getDescription() != null) {
                foundedTour.setDescription(tourDto.getDescription());
            }
            if (tourDto.getFrom_location()!= null) {
                foundedTour.setFrom_location(tourDto.getFrom_location());
            }
            if (tourDto.getTo_location() != null) {
                foundedTour.setTo_location(tourDto.getTo_location());
            }
            if (tourDto.getTransportType() != null) {
                foundedTour.setTransportType(tourDto.getTransportType());
            }
            TourEntity updatedTourEntity = tourRepository.save(foundedTour);
            return new TourDto(updatedTourEntity.getId(), updatedTourEntity.getName(), updatedTourEntity.getDescription(), updatedTourEntity.getFrom_location(), updatedTourEntity.getTo_location(), updatedTourEntity.getTransportType(), updatedTourEntity.getDistance(), updatedTourEntity.getTimeEst());

        }
        return null;
    }

    @Override
    public void deleteTour(Long id) {
        tourRepository.deleteById(id);
    }
}
