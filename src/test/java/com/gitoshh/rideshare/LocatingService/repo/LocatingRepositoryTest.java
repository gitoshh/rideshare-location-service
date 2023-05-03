package com.gitoshh.rideshare.LocatingService.repo;

import com.gitoshh.rideshare.LocatingService.entity.LocationTracker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocatingRepositoryTest {

    @Autowired
    private LocatingRepository underTest;

    @BeforeEach
    void setUp() {
        LocationTracker locationTracker = LocationTracker.builder()
                .userId(10L)
                .latitude(1.0)
                .longitude(1.0)
                .isActive(true)
                .isDriver(true)
                .build();
        underTest.save(locationTracker);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itFindsCurrentLocationByUserId() {
        // when
        boolean exists = underTest.findByUserId(10L).isPresent();
        // then
        assertTrue(exists);

        // when
        boolean unusedEmailExists = underTest.findByUserId(20L).isPresent();
        assertFalse(unusedEmailExists);
    }

    @Test
    void itFetchesTheClosestDriver() {
        // when
        Optional<LocationTracker> locationTracker = underTest.findClosestDriver(-1.1907530984957566, 36.771114337065036);
        // then
        assertNotNull(locationTracker);
    }
}