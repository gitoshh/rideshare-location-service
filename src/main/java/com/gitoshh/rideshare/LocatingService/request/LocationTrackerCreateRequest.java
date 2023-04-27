package com.gitoshh.rideshare.LocatingService.request;

import lombok.NonNull;

public record LocationTrackerCreateRequest(
        @NonNull
        Long userId,
        @NonNull
        double latitude,
        @NonNull
        double longitude,
        @NonNull
        boolean isDriver,
        @NonNull
        boolean isActive
) {
}
