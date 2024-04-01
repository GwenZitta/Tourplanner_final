package com.example.tourplanner.service.mapper;

import com.example.tourplanner.persistence.entities.TourEntity;
import com.example.tourplanner.service.dtos.TourDto;
import org.springframework.stereotype.Service;

@Service
public class TourMapper extends AbstractMapper<TourEntity, TourDto>{

    @Override
    public TourDto mapToDto(TourEntity source) {
        return TourDto.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .from_location(source.getFrom_location())
                .to_location(source.getTo_location())
                .transportType(source.getTransportType())
                .build();
    }
}
