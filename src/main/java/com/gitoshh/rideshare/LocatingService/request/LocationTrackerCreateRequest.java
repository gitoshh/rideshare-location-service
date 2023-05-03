package com.gitoshh.rideshare.LocatingService.request;

import lombok.Builder;
import lombok.NonNull;

@Builder
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
