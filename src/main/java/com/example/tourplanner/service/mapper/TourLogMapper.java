package com.example.tourplanner.service.mapper;

import com.example.tourplanner.persistence.entities.TourLogEntity;
import com.example.tourplanner.service.dtos.TourLogDto;
import org.springframework.stereotype.Service;

@Service
public class TourLogMapper extends AbstractMapper<TourLogEntity, TourLogDto>{

    @Override
    public TourLogDto mapToDto(TourLogEntity source) {
        return TourLogDto.builder()
                .id(source.getId())
                .tour(source.getTour())
                .datetime(source.getDatetime())
                .comment(source.getComment())
                .difficulty(source.getDifficulty())
                .distance(source.getDistance())
                .time(source.getTime())
                .rating(source.getRating())
                .build();
    }
}
