package com.example.tourplanner;

import com.example.tourplanner.persistence.entities.TourEntity;
import com.example.tourplanner.persistence.repositories.TourRepository;
import com.example.tourplanner.service.TourService;
import com.example.tourplanner.service.dtos.TourDto;
import com.example.tourplanner.service.impl.TourServiceImpl;
import com.example.tourplanner.persistence.entities.TourLogEntity;
import com.example.tourplanner.persistence.repositories.TourLogRepository;
import com.example.tourplanner.service.TourLogService;
import com.example.tourplanner.service.dtos.TourLogDto;
import com.example.tourplanner.service.impl.TourLogServiceImpl;
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
    private TourLogDto tourLogDto;
    @Autowired
    private TourServiceImpl tour_service = new TourServiceImpl();
    @Autowired
    private TourLogServiceImpl tourlog_service = new TourLogServiceImpl();

    @Autowired
    private TourRepository tour_repository;
    @Autowired
    private TourLogRepository tourlog_repository;

    @BeforeEach
    public void setupTestData() {
        // Given : Setup object or precondition
        tourDto = TourDto.builder()
                .name("name")
                .description("description")
                .from_location("Vienna")
                .to_location("Mödling")
                .transportType("transportType")
                .distance("distance")
                .duration("duration")
                .map("map")
                .build();

        tourLogDto = TourLogDto.builder()
                .comment("comment")
                .difficulty("difficulty")
                .distance("distance")
                .time("time")
                .rating("rating")
                .build();
    }

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(tour_service).isNotNull();
        assertThat(tour_repository).isNotNull();
        assertThat(tourlog_service).isNotNull();
        assertThat(tourlog_repository).isNotNull();
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
    @DisplayName("JUnit test for get all tours")
    public void getAllTours() {
        Long saved_id1 = tour_service.saveNewTour(tourDto);
        Long saved_id2 = tour_service.saveNewTour(tourDto);
        Long saved_id3 = tour_service.saveNewTour(tourDto);
        List<TourEntity> saved = tour_repository.findAll();
        assertTrue(saved.size() >= 3);
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
    @DisplayName("JUnit test find tour by id")
    public void testFindTourById() {
        Long id = tour_service.saveNewTour(tourDto);
        TourEntity saved = tour_repository.findByTourId(id);
        assertEquals(saved.getDescription(), tourDto.getDescription());
        assertEquals(saved.getFrom_location(), tourDto.getFrom_location());
        assertEquals(saved.getTo_location(), tourDto.getTo_location());
        assertEquals(saved.getTransportType(), tourDto.getTransportType());
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

    @Test
    @DisplayName("JUnit test find tour by name with wrong name")
    public void testFindTourByNameNull() {
        tour_service.saveNewTour(tourDto);
        List<TourEntity> saved = tour_repository.findByTourName("wrongname");
        assertTrue(saved.isEmpty());
    }

    @Test
    @DisplayName("JUnit test find tour by name with multiple tours")
    public void testFindTourByNameWithMultipleTours() {
        tour_service.saveNewTour(tourDto);
        tour_service.saveNewTour(tourDto);
        List<TourEntity> saved = tour_repository.findByTourName(tourDto.getName());
        assertEquals(saved.get(0).getDescription(), tourDto.getDescription());
        assertEquals(saved.get(0).getFrom_location(), tourDto.getFrom_location());
        assertEquals(saved.get(0).getTo_location(), tourDto.getTo_location());
        assertEquals(saved.get(0).getTransportType(), tourDto.getTransportType());
        assertEquals(saved.get(1).getDescription(), tourDto.getDescription());
        assertEquals(saved.get(1).getFrom_location(), tourDto.getFrom_location());
        assertEquals(saved.get(1).getTo_location(), tourDto.getTo_location());
        assertEquals(saved.get(1).getTransportType(), tourDto.getTransportType());
    }


    @Test
    @DisplayName("JUnit test for update tour name")
    public void updateTourName() {
        Long saved_id = tour_service.saveNewTour(tourDto);
        TourEntity saved = tour_repository.findByTourId(saved_id);
        saved.setName("updated name");
        tour_repository.save(saved);
        TourEntity afterUpdate = tour_repository.findByTourId(saved_id);
        assertEquals(afterUpdate.getName(), "updated name");
    }
    @Test
    @DisplayName("JUnit test for update tour description")
    public void updateTourDescription() {
        Long saved_id = tour_service.saveNewTour(tourDto);
        TourEntity saved = tour_repository.findByTourId(saved_id);
        saved.setDescription("updated description");
        tour_repository.save(saved);
        TourEntity afterUpdate = tour_repository.findByTourId(saved_id);
        assertEquals(afterUpdate.getDescription(), "updated description");
    }
    @Test
    @DisplayName("JUnit test for update tour transport type")
    public void updateTourTransportType() {
        Long saved_id = tour_service.saveNewTour(tourDto);
        TourEntity saved = tour_repository.findByTourId(saved_id);
        saved.setTransportType("updated transport type");
        tour_repository.save(saved);
        TourEntity afterUpdate = tour_repository.findByTourId(saved_id);
        assertEquals(afterUpdate.getTransportType(), "updated transport type");
    }
    @Test
    @DisplayName("JUnit test for update tour from_location")
    public void updateTourFromLocation() {
        Long saved_id = tour_service.saveNewTour(tourDto);
        TourEntity saved = tour_repository.findByTourId(saved_id);
        saved.setFrom_location("Paris");
        tour_repository.save(saved);
        TourEntity afterUpdate = tour_repository.findByTourId(saved_id);
        assertEquals(afterUpdate.getFrom_location(), "Paris");
    }
    @Test
    @DisplayName("JUnit test for update tour to_location")
    public void updateTourToLocation() {
        Long saved_id = tour_service.saveNewTour(tourDto);
        TourEntity saved = tour_repository.findByTourId(saved_id);
        saved.setTo_location("Brüssel");
        tour_repository.save(saved);
        TourEntity afterUpdate = tour_repository.findByTourId(saved_id);
        assertEquals(afterUpdate.getTo_location(), "Brüssel");
    }
    @Test
    @DisplayName("JUnit test for update tour with multiple changes")
    public void updateTourMultipleChanges() {
        Long saved_id = tour_service.saveNewTour(tourDto);
        TourEntity saved = tour_repository.findByTourId(saved_id);
        saved.setDescription("new description");
        saved.setName("new name");
        tour_repository.save(saved);
        TourEntity afterUpdate = tour_repository.findByTourId(saved_id);
        assertEquals(afterUpdate.getDescription(), "new description");
        assertEquals(afterUpdate.getName(), "new name");
    }




//   Tests for TourLog

    @Test
    @DisplayName("JUnit test for save new tourlog")
    public void saveNewTourLog() {
        List<TourEntity> tour = tour_repository.findAll();
        tourLogDto.setTourid(tour.get(0).getId());
        Long saved_id = tourlog_service.saveNewTourLog(tourLogDto);
        TourLogEntity saved = tourlog_repository.findByTourLogId(saved_id);
        assertEquals(saved.getId(), saved_id);
    }

    @Test
    @DisplayName("JUnit test for get tourlogs by tourid")
    public void getTourLogsByTourId() {
        List<TourEntity> tour = tour_repository.findAll();
        tourLogDto.setTourid(tour.get(0).getId());
        Long saved_id1 = tourlog_service.saveNewTourLog(tourLogDto);
        Long saved_id2 = tourlog_service.saveNewTourLog(tourLogDto);
        Long saved_id3 = tourlog_service.saveNewTourLog(tourLogDto);
        List<TourLogEntity> saved = tourlog_repository.findByTourLogTourId(tour.get(0).getId());
        assertTrue(saved.size() >= 3);
    }

    @Test
    @DisplayName("JUnit test for get tourlog by id")
    public void getTourLogsById() {
        List<TourEntity> tour = tour_repository.findAll();
        tourLogDto.setTourid(tour.get(0).getId());
        Long saved = tourlog_service.saveNewTourLog(tourLogDto);
        TourLogEntity found = tourlog_repository.findByTourLogId(saved);
        assertEquals(found.getComment(), tourLogDto.getComment());
    }

    @Test
    @DisplayName("JUnit test for get tourlog by id no result")
    public void getTourLogsByIdNoResult() {
        List<TourEntity> tour = tour_repository.findAll();
        tourLogDto.setTourid(tour.get(0).getId());
        Long saved = tourlog_service.saveNewTourLog(tourLogDto);
        TourLogEntity found = tourlog_repository.findByTourLogId(12345L);
        assertEquals(found, null);
    }

    @Test
    @DisplayName("JUnit test for get all tourlogs")
    public void getAllTourLogs() {
        List<TourEntity> tour = tour_repository.findAll();
        tourLogDto.setTourid(tour.get(0).getId());
        Long saved_id1 = tourlog_service.saveNewTourLog(tourLogDto);
        Long saved_id2 = tourlog_service.saveNewTourLog(tourLogDto);
        Long saved_id3 = tourlog_service.saveNewTourLog(tourLogDto);
        List<TourLogEntity> saved = tourlog_repository.findAll();
        assertTrue(saved.size() >= 3);
    }

    @Test
    @DisplayName("JUnit test for update tourlog comment")
    public void updateTourLogComment() {
        List<TourEntity> tour = tour_repository.findAll();
        tourLogDto.setTourid(tour.get(0).getId());
        Long saved_id = tourlog_service.saveNewTourLog(tourLogDto);
        TourLogEntity saved = tourlog_repository.findByTourLogId(saved_id);
        saved.setComment("updated comment");
        tourlog_repository.save(saved);
        TourLogEntity afterUpdate = tourlog_repository.findByTourLogId(saved_id);
        assertEquals(afterUpdate.getComment(), "updated comment");
    }
    @Test
    @DisplayName("JUnit test for update tourlog difficulty")
    public void updateTourLogDifficulty() {
        List<TourEntity> tour = tour_repository.findAll();
        tourLogDto.setTourid(tour.get(0).getId());
        Long saved_id = tourlog_service.saveNewTourLog(tourLogDto);
        TourLogEntity saved = tourlog_repository.findByTourLogId(saved_id);
        saved.setDifficulty("updated difficulty");
        tourlog_repository.save(saved);
        TourLogEntity afterUpdate = tourlog_repository.findByTourLogId(saved_id);
        assertEquals(afterUpdate.getDifficulty(), "updated difficulty");
    }
    @Test
    @DisplayName("JUnit test for update tourlog distance")
    public void updateTourLogDistance() {
        List<TourEntity> tour = tour_repository.findAll();
        tourLogDto.setTourid(tour.get(0).getId());
        Long saved_id = tourlog_service.saveNewTourLog(tourLogDto);
        TourLogEntity saved = tourlog_repository.findByTourLogId(saved_id);
        saved.setDistance("updated distance");
        tourlog_repository.save(saved);
        TourLogEntity afterUpdate = tourlog_repository.findByTourLogId(saved_id);
        assertEquals(afterUpdate.getDistance(), "updated distance");
    }
    @Test
    @DisplayName("JUnit test for update tourlog time")
    public void updateTourLogTime() {
        List<TourEntity> tour = tour_repository.findAll();
        tourLogDto.setTourid(tour.get(0).getId());
        Long saved_id = tourlog_service.saveNewTourLog(tourLogDto);
        TourLogEntity saved = tourlog_repository.findByTourLogId(saved_id);
        saved.setTime("updated time");
        tourlog_repository.save(saved);
        TourLogEntity afterUpdate = tourlog_repository.findByTourLogId(saved_id);
        assertEquals(afterUpdate.getTime(), "updated time");
    }
    @Test
    @DisplayName("JUnit test for update tourlog rating")
    public void updateTourLogRating() {
        List<TourEntity> tour = tour_repository.findAll();
        tourLogDto.setTourid(tour.get(0).getId());
        Long saved_id = tourlog_service.saveNewTourLog(tourLogDto);
        TourLogEntity saved = tourlog_repository.findByTourLogId(saved_id);
        saved.setRating("updated rating");
        tourlog_repository.save(saved);
        TourLogEntity afterUpdate = tourlog_repository.findByTourLogId(saved_id);
        assertEquals(afterUpdate.getRating(), "updated rating");
    }
    @Test
    @DisplayName("JUnit test for update tourlog with multiple changes")
    public void updateTourLogMultipleChanges() {
        List<TourEntity> tour = tour_repository.findAll();
        tourLogDto.setTourid(tour.get(0).getId());
        Long saved_id = tourlog_service.saveNewTourLog(tourLogDto);
        TourLogEntity saved = tourlog_repository.findByTourLogId(saved_id);
        saved.setComment("new comment");
        saved.setDifficulty("new difficulty");
        tourlog_repository.save(saved);
        TourLogEntity afterUpdate = tourlog_repository.findByTourLogId(saved_id);
        assertEquals(afterUpdate.getComment(), "new comment");
        assertEquals(afterUpdate.getDifficulty(), "new difficulty");
    }

}
