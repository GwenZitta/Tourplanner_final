package com.example.tourplanner.service.impl;

import com.example.tourplanner.persistence.entities.TourLogEntity;
import com.example.tourplanner.persistence.repositories.TourLogRepository;
import com.example.tourplanner.service.TourLogService;
import com.example.tourplanner.service.dtos.TourLogDto;
import com.example.tourplanner.service.mapper.TourLogMapper;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TourLogServiceImpl implements TourLogService {

    @Autowired
    private TourLogRepository tourLogRepository;
    @Autowired
    private TourLogMapper tourLogMapper;

    @Override
    public Long saveNewTourLog(TourLogDto tourLogDto) {
        TourLogEntity te = new TourLogEntity(Long.MIN_VALUE, tourLogDto.getDatetime(), tourLogDto.getComment(), tourLogDto.getDifficulty(), tourLogDto.getDistance(), tourLogDto.getTime(), tourLogDto.getRating(), tourLogDto.getTour());
        TourLogEntity saved=tourLogRepository.save(te);
        return saved.getId();
    }

    @Override
    public List<TourLogDto> getAllTourLogs() {
        System.out.println("handle get all tour log request");
        List<TourLogDto> list_tourlogs = new ArrayList<>();
        List<TourLogEntity> list_tourlogslog = tourLogRepository.findAll();
        for (TourLogEntity te : list_tourlogslog) {
            list_tourlogs.add(new TourLogDto(te.getId(), te.getTour(), te.getDatetime(), te.getComment(), te.getDifficulty(), te.getDistance(), te.getTime(), te.getRating()));
        }
        return list_tourlogs;
    }


    @Override
    public List<TourLogDto> getTourLogById(Long id) {
        List<TourLogDto> list_tourlogs = new ArrayList<>();
        TourLogEntity tourlog_entity = tourLogRepository.findByTourLogId(id);
        list_tourlogs.add(new TourLogDto(tourlog_entity.getId(), tourlog_entity.getTour(), tourlog_entity.getDatetime(), tourlog_entity.getComment(), tourlog_entity.getDifficulty(), tourlog_entity.getDistance(), tourlog_entity.getTime(), tourlog_entity.getRating()));
        return list_tourlogs;
    }

    @Override
    public List<TourLogDto> getTourLogByTourId(Long id) {
        List<TourLogDto> list_tourlogs = new ArrayList<>();
        TourLogEntity tourlog_entity = tourLogRepository.findByTourLogTourId(id);
        list_tourlogs.add(new TourLogDto(tourlog_entity.getId(), tourlog_entity.getTour(), tourlog_entity.getDatetime(), tourlog_entity.getComment(), tourlog_entity.getDifficulty(), tourlog_entity.getDistance(), tourlog_entity.getTime(), tourlog_entity.getRating()));
        return list_tourlogs;
    }

    @Override
    public TourLogDto updateTourLog(TourLogDto tourLogDto) {
        TourLogEntity foundTourLog = tourLogRepository.findByTourLogId(tourLogDto.getId());
        if (foundTourLog != null) {
            if (tourLogDto.getTour() != null) {
                foundTourLog.setTour(tourLogDto.getTour());
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
            return new TourLogDto(updatedTourlogEntity.getId(), updatedTourlogEntity.getTour(), updatedTourlogEntity.getDatetime(), updatedTourlogEntity.getComment(), updatedTourlogEntity.getDifficulty(), updatedTourlogEntity.getDistance(), updatedTourlogEntity.getTime(), updatedTourlogEntity.getRating());

        }
        return null;
    }

    @Override
    public void deleteTourLog(Long id) {
        tourLogRepository.deleteById(id);
    }
}
