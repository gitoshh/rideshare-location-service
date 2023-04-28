package com.gitoshh.rideshare.LocatingService.controller;


import com.gitoshh.rideshare.LocatingService.entity.LocationTracker;
import com.gitoshh.rideshare.LocatingService.request.LocationTrackerCreateRequest;
import com.gitoshh.rideshare.LocatingService.request.LocationTrackerGetClosestDriverRequest;
import com.gitoshh.rideshare.LocatingService.service.LocationTrackerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/locations")
public record LocatingController(LocationTrackerService locatingService) {

    @GetMapping("/by/user/{userId}")
    public ResponseEntity<LocationTracker> getLocationTrackerByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(locatingService.getLocationTrackerByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<LocationTracker> createLocationTracker(@Valid LocationTrackerCreateRequest locationTrackerCreateRequest) {
        return ResponseEntity.ok(locatingService.createLocationTracker(locationTrackerCreateRequest));
    }

    @PostMapping("/closest-driver")
    public ResponseEntity<LocationTracker> getClosestDriver(@Valid @RequestBody LocationTrackerGetClosestDriverRequest locationTrackerCreateRequest) {
        return ResponseEntity.ok(locatingService.getClosestDriver(locationTrackerCreateRequest));
    }

    @PostMapping("by/user/{userId}/activate")
    public ResponseEntity<LocationTracker> activateLocationTracker(@PathVariable Long userId) {
        return ResponseEntity.ok(locatingService.activateLocationTracker(userId));
    }

    @PostMapping("by/user/{userId}/deactivate")
    public ResponseEntity<LocationTracker> deactivateLocationTracker(@PathVariable Long userId) {
        return ResponseEntity.ok(locatingService.deactivateLocationTracker(userId));
    }
}
