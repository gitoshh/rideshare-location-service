package com.gitoshh.rideshare.LocatingService.request;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record LocationTrackerGetClosestDriverRequest(
        @NonNull
        double latitude,
        @NonNull
        double longitude
) {
}
