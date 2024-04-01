package com.example.tourplanner;

import com.example.tourplanner.persistence.entities.TourEntity;
import com.example.tourplanner.persistence.repositories.TourRepository;
import com.example.tourplanner.service.TourService;
import com.example.tourplanner.service.dtos.TourDto;
import com.example.tourplanner.service.impl.TourServiceImpl;
import java.util.List;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class TourplannerApplicationTests {

    private TourDto tourDto;
    @Autowired
    private TourServiceImpl tour_service = new TourServiceImpl();

    @Autowired
    private TourRepository tour_repository;

    @BeforeEach
    public void setupTestData() {
        // Given : Setup object or precondition
        tourDto = TourDto.builder()
                .name("name")
                .description("description")
                .from_location("from_location")
                .to_location("to_location")
                .transportType("transportType")
                .build();
    }

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(tour_service).isNotNull();
        assertThat(tour_repository).isNotNull();
    }

    // JUnit Test for save employee operation
    @Test
    @DisplayName("JUnit test for save new tour")
    public void saveNewTour() {
        Long saved_id = tour_service.saveNewTour(tourDto);
        TourEntity saved = tour_repository.findByTourId(saved_id);
        assertEquals(saved.getId(), saved_id);

    }

    @Test
    @DisplayName("JUnit test for delete  tour")
    public void saveTour_then_deleteIt() {
        Long saved_id = tour_service.saveNewTour(tourDto);
        TourEntity saved = tour_repository.findByTourId(saved_id);
        tour_repository.delete(saved);
        TourEntity foundedAgain = tour_repository.findByTourId(saved_id);
        assertEquals(foundedAgain, null);
    }

    @Test
    @DisplayName("JUnit test for update tour")
    public void updateTour() {
        Long saved_id = tour_service.saveNewTour(tourDto);
        TourEntity saved = tour_repository.findByTourId(saved_id);
        saved.setDescription("updated description");
        tour_repository.save(saved);
        TourEntity afterUpdate = tour_repository.findByTourId(saved_id);
        assertEquals(afterUpdate.getDescription(), "updated description");
    }

    @Test
    @DisplayName("JUnit test find tour by name")
    public void testFindTourByName() {
        tour_service.saveNewTour(tourDto);
        List<TourEntity> saved = tour_repository.findByTourName(tourDto.getName());
        assertEquals(saved.get(0).getDescription(), tourDto.getDescription());
        assertEquals(saved.get(0).getFrom_location(), tourDto.getFrom_location());
        assertEquals(saved.get(0).getTo_location(), tourDto.getTo_location());
        assertEquals(saved.get(0).getTransportType(), tourDto.getTransportType());

    }

}
