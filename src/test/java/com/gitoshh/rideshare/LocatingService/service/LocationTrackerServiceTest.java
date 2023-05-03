package com.gitoshh.rideshare.LocatingService.service;

import com.gitoshh.rideshare.LocatingService.entity.LocationTracker;
import com.gitoshh.rideshare.LocatingService.repo.LocatingRepository;
import com.gitoshh.rideshare.LocatingService.request.LocationTrackerCreateRequest;
import com.gitoshh.rideshare.LocatingService.request.LocationTrackerGetClosestDriverRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class LocationTrackerServiceTest {

    @InjectMocks
    private LocationTrackerService locationTrackerService;
    @Mock
    private LocatingRepository locatingRepository;

    private LocationTracker locationTracker;


    @BeforeEach
    void setUp() {
        locationTracker = LocationTracker.builder()
                .userId(10L)
                .latitude(1.0)
                .longitude(1.0)
                .isActive(true)
                .isDriver(true)
                .build();

    }

    @Test
    void createLocationTracker() {
        // When
        LocationTrackerCreateRequest locationTrackerCreateRequest = LocationTrackerCreateRequest.builder()
                .userId(20L)
                .latitude(1.0)
                .longitude(1.0)
                .isActive(true)
                .isDriver(true)
                .build();
        locationTrackerService.createLocationTracker(locationTrackerCreateRequest);

        // Then
        ArgumentCaptor<LocationTracker> locationTrackerArgumentCaptor = ArgumentCaptor.forClass(LocationTracker.class);
        verify(locatingRepository).save(locationTrackerArgumentCaptor.capture());
        LocationTracker capturedLocationTracker = locationTrackerArgumentCaptor.getValue();
        assertEquals(20L, capturedLocationTracker.getUserId());
    }

    @Test
    void getLocationTrackerByUserId() {
        // given
        Optional<LocationTracker> optionalLocationTracker = Optional.of(locationTracker);
        doReturn(optionalLocationTracker).when(locatingRepository).findByUserId(10L);

        // when
        LocationTracker locationTracker1 = locationTrackerService.getLocationTrackerByUserId(10L);
        // verify
        assertEquals(locationTracker1, locationTracker);
    }

    @Test
    void getClosestDriver() {
        // given
        Optional<LocationTracker> optionalLocationTracker = Optional.of(locationTracker);
        doReturn(optionalLocationTracker).when(locatingRepository).findClosestDriver(1.0, 1.0);

        // when
        LocationTrackerGetClosestDriverRequest locationTrackerGetClosestDriverRequest = LocationTrackerGetClosestDriverRequest.builder()
                .latitude(1.0)
                .longitude(1.0)
                .build();
        LocationTracker locationTracker1 = locationTrackerService.getClosestDriver(locationTrackerGetClosestDriverRequest);
        // verify
        assertEquals(locationTracker1, locationTracker);
    }

    @Test
    void activateLocationTracker() {
        // Given
        doReturn(Optional.of(locationTracker)).when(locatingRepository).findByUserId(10L);
        // When
        locationTrackerService.activateLocationTracker(10L);
        // Then
        ArgumentCaptor<LocationTracker> locationTrackerArgumentCaptor = ArgumentCaptor.forClass(LocationTracker.class);
        verify(locatingRepository).save(locationTrackerArgumentCaptor.capture());
        LocationTracker capturedLocationTracker = locationTrackerArgumentCaptor.getValue();
        assertEquals(10L, capturedLocationTracker.getUserId());
        assertTrue(capturedLocationTracker.isActive());
    }

    @Test
    void deactivateLocationTracker() {
        // Given
        doReturn(Optional.of(locationTracker)).when(locatingRepository).findByUserId(10L);
        // When
        locationTrackerService.deactivateLocationTracker(10L);
        // Then
        ArgumentCaptor<LocationTracker> locationTrackerArgumentCaptor = ArgumentCaptor.forClass(LocationTracker.class);
        verify(locatingRepository).save(locationTrackerArgumentCaptor.capture());
        LocationTracker capturedLocationTracker = locationTrackerArgumentCaptor.getValue();
        assertEquals(10L, capturedLocationTracker.getUserId());
        assertFalse(capturedLocationTracker.isActive());
    }
}