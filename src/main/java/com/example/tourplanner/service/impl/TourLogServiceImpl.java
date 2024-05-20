package com.example.tourplanner.service.impl;

import com.example.tourplanner.persistence.entities.TourEntity;
import com.example.tourplanner.persistence.entities.TourLogEntity;
import com.example.tourplanner.persistence.repositories.TourLogRepository;
import com.example.tourplanner.service.TourLogService;
import com.example.tourplanner.service.TourService;
import com.example.tourplanner.service.dtos.TourDto;
import com.example.tourplanner.service.dtos.TourLogDto;
import com.example.tourplanner.service.mapper.TourLogMapper;
import java.util.ArrayList;

import com.example.tourplanner.service.mapper.TourMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TourLogServiceImpl implements TourLogService {

    @Autowired
    private TourLogRepository tourLogRepository;
    @Autowired
    private TourLogMapper tourLogMapper;
    @Autowired
    private TourService tourService;

    @Override
    public Long saveNewTourLog(TourLogDto tourLogDto) {
        TourDto tourDto = tourService.getTourById(tourLogDto.getTourid()).get(0);
        TourEntity te = new TourEntity(tourDto.getId(), tourDto.getName(), tourDto.getDescription(), tourDto.getFrom_location(), tourDto.getTo_location(), tourDto.getTransportType(), "", "", "");
        TourLogEntity tle = new TourLogEntity(Long.MIN_VALUE, tourLogDto.getDatetime(), tourLogDto.getComment(), tourLogDto.getDifficulty(), tourLogDto.getDistance(), tourLogDto.getTime(), tourLogDto.getRating(), te);
        TourLogEntity saved = tourLogRepository.save(tle);
        return saved.getId();
    }

    @Override
    public List<TourLogDto> getAllTourLogs() {
        System.out.println("handle get all tour log request");
        List<TourLogDto> list_tourlogs = new ArrayList<>();
        List<TourLogEntity> list_tourlogslog = tourLogRepository.findAll();
        for (TourLogEntity te : list_tourlogslog) {
            list_tourlogs.add(new TourLogDto(te.getId(), te.getTour().getId(), te.getDatetime(), te.getComment(), te.getDifficulty(), te.getDistance(), te.getTime(), te.getRating()));
        }
        return list_tourlogs;
    }


    @Override
    public List<TourLogDto> getTourLogById(Long id) {
        List<TourLogDto> list_tourlogs = new ArrayList<>();
        TourLogEntity tourlog_entity = tourLogRepository.findByTourLogId(id);
        list_tourlogs.add(new TourLogDto(tourlog_entity.getId(), tourlog_entity.getTour().getId(), tourlog_entity.getDatetime(), tourlog_entity.getComment(), tourlog_entity.getDifficulty(), tourlog_entity.getDistance(), tourlog_entity.getTime(), tourlog_entity.getRating()));
        return list_tourlogs;
    }

    @Override
    public List<TourLogDto> getTourLogByTourId(Long id) {
        List<TourLogDto> list_tourlogs = new ArrayList<>();
        List<TourLogEntity> tourlog_entity = tourLogRepository.findByTourLogTourId(id);
        for (int i = 0; i < tourlog_entity.size(); i++) {
            list_tourlogs.add(new TourLogDto(tourlog_entity.get(i).getId(), tourlog_entity.get(i).getTour().getId(), tourlog_entity.get(i).getDatetime(), tourlog_entity.get(i).getComment(), tourlog_entity.get(i).getDifficulty(), tourlog_entity.get(i).getDistance(), tourlog_entity.get(i).getTime(), tourlog_entity.get(i).getRating()));
        }
        return list_tourlogs;
    }

    @Override
    public TourLogDto updateTourLog(TourLogDto tourLogDto) {
        TourLogEntity foundTourLog = tourLogRepository.findByTourLogId(tourLogDto.getId());
        if (foundTourLog != null) {
            if (tourLogDto.getTourid() != null) {
                TourDto tourDto = tourService.getTourById(tourLogDto.getTourid()).get(0);
                TourEntity te = new TourEntity(tourDto.getId(), tourDto.getName(), tourDto.getDescription(), tourDto.getFrom_location(), tourDto.getTo_location(), tourDto.getTransportType(), "", "", "");
                foundTourLog.setTour(te);
            }
            if (tourLogDto.getDatetime() != null) {
                foundTourLog.setDatetime(tourLogDto.getDatetime());
            }
            if (tourLogDto.getComment()!= null) {
                foundTourLog.setComment(tourLogDto.getComment());
            }
            if (tourLogDto.getDifficulty() != null) {
                foundTourLog.setDifficulty(tourLogDto.getDifficulty());
            }
            if (tourLogDto.getDistance() != null) {
                foundTourLog.setDistance(tourLogDto.getDistance());
            }
            if (tourLogDto.getTime() != null) {
                foundTourLog.setTime(tourLogDto.getTime());
            }
            if (tourLogDto.getRating() != null) {
                foundTourLog.setRating(tourLogDto.getRating());
            }
            TourLogEntity updatedTourlogEntity = tourLogRepository.save(foundTourLog);
            return new TourLogDto(updatedTourlogEntity.getId(), updatedTourlogEntity.getTour().getId(), updatedTourlogEntity.getDatetime(), updatedTourlogEntity.getComment(), updatedTourlogEntity.getDifficulty(), updatedTourlogEntity.getDistance(), updatedTourlogEntity.getTime(), updatedTourlogEntity.getRating());

        }
        return null;
    }

    @Override
    public void deleteTourLog(Long id) {
        tourLogRepository.deleteById(id);
    }
}
