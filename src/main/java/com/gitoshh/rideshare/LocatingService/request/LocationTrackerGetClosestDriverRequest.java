package com.gitoshh.rideshare.LocatingService.request;

import lombok.NonNull;

public record LocationTrackerGetClosestDriverRequest(
        @NonNull
        double latitude,
        @NonNull
        double longitude
) {
}
