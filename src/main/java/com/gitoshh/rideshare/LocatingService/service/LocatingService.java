package com.gitoshh.rideshare.LocatingService.service;

import com.gitoshh.rideshare.LocatingService.entity.LocationTracker;
import com.gitoshh.rideshare.LocatingService.exception.NotFoundException;
import com.gitoshh.rideshare.LocatingService.repo.LocatingRepository;
import com.gitoshh.rideshare.LocatingService.request.LocationTrackerCreateRequest;
import com.gitoshh.rideshare.LocatingService.request.LocationTrackerGetClosestDriverRequest;
import org.springframework.stereotype.Service;

@Service
public record LocatingService(LocatingRepository locatingRepository) {
    /**
     * Create or update the location tracker
     *
     * @param locationTrackerCreateRequest request body
     * @return location tracker
     */
    public LocationTracker createLocationTracker(LocationTrackerCreateRequest locationTrackerCreateRequest) {
        LocationTracker locationTracker = getLocationTrackerByUserId(locationTrackerCreateRequest.userId());

        if (locationTracker != null) {
            locationTracker.setLatitude(locationTrackerCreateRequest.latitude());
            locationTracker.setLongitude(locationTrackerCreateRequest.longitude());
            return locatingRepository.save(locationTracker);
        } else {
            LocationTracker newLocationTracker = LocationTracker.builder()
                    .userId(locationTrackerCreateRequest.userId())
                    .latitude(locationTrackerCreateRequest.latitude())
                    .longitude(locationTrackerCreateRequest.longitude())
                    .build();
            return locatingRepository.save(newLocationTracker);
        }

    }

    /**
     * Get location tracker by user id
     *
     * @param userId - user id
     * @return - location tracker
     */
    public LocationTracker getLocationTrackerByUserId(Long userId) {
        return locatingRepository.findByUserId(userId).orElseThrow(() ->
                new NotFoundException("Location tracker not found"));
    }

    /**
     * Get the closest driver
     * @param locationTrackerCreateRequest request body
     * @return location tracker
     */
    public LocationTracker getClosestDriver(LocationTrackerGetClosestDriverRequest locationTrackerCreateRequest) {
        return locatingRepository.findClosestDriver(
                locationTrackerCreateRequest.latitude(),
                locationTrackerCreateRequest.longitude()
        ).orElseThrow(() -> new NotFoundException("No available drivers"));
    }

    /**
     * Activate location tracker
     * @param userId - user id
     * @return location tracker
     */
    public LocationTracker activateLocationTracker(Long userId) {
        LocationTracker locationTracker = getLocationTrackerByUserId(userId);
        locationTracker.setActive(true);
        return locatingRepository.save(locationTracker);
    }

    /**
     * Deactivate location tracker
     * @param userId - user id
     * @return location tracker
     */
    public LocationTracker deactivateLocationTracker(Long userId) {
        LocationTracker locationTracker = getLocationTrackerByUserId(userId);
        locationTracker.setActive(false);
        return locatingRepository.save(locationTracker);
    }
}
