package com.gitoshh.rideshare.LocatingService.repo;


import com.gitoshh.rideshare.LocatingService.entity.LocationTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocatingRepository extends JpaRepository<LocationTracker, Long> {

    @Query("SELECT l FROM LocationTracker l WHERE l.userId = ?1")
    Optional<LocationTracker> findByUserId(Long userId);

    @Query(value = "SELECT *, (3959 * acos(cos(radians(37.423021)) * cos(radians(:latitude)) * cos(radians(:longitude) - radians(-122.083739)) + sin(radians(37.423021)) * sin(radians(:latitude)))) AS distance FROM location_tracker l WHERE l.is_driver = TRUE && l.is_active = TRUE HAVING distance < 10000 ORDER BY distance LIMIT 1", nativeQuery = true)
    Optional<LocationTracker> findClosestDriver(double latitude, double longitude);
}
