package com.gitoshh.rideshare.LocatingService.runners;

import com.gitoshh.rideshare.LocatingService.entity.LocationTracker;
import com.gitoshh.rideshare.LocatingService.repo.LocatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
@ConditionalOnProperty(name = "environment.name", havingValue = "local")
public class MyRunner implements CommandLineRunner {

    private final LocatingRepository locatingRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Saving location data");
        LocationTracker locationTracker = LocationTracker.builder()
                .userId(1L)
                .isDriver(true)
                .isActive(true)
                .longitude(36.771114337065036)
                .latitude(-1.1907530984957566)
                .build();
        locatingRepository.save(locationTracker);
        LocationTracker locationTracker1 = LocationTracker.builder()
                .userId(2L)
                .isDriver(true)
                .isActive(true)
                .longitude(36.763427605676384)
                .latitude(-1.198645526786519)
                .build();
        locatingRepository.save(locationTracker1);
        LocationTracker locationTracker2 = LocationTracker.builder()
                .userId(3L)
                .isDriver(true)
                .isActive(true)
                .longitude(36.80378648531493)
                .latitude(-1.1426943103972358)
                .build();
        locatingRepository.save(locationTracker2);

        LocationTracker locationTracker3 = LocationTracker.builder()
                .userId(4L)
                .isDriver(false)
                .isActive(true)
                .longitude(36.771114337065036)
                .latitude(-1.1907530984957566)
                .build();
        locatingRepository.save(locationTracker3);

        LocationTracker locationTracker4 = LocationTracker.builder()
                .userId(5L)
                .isDriver(true)
                .isActive(false)
                .longitude(36.771114337065036)
                .latitude(-1.1907530984957566)
                .build();
        locatingRepository.save(locationTracker4);
    }
}
