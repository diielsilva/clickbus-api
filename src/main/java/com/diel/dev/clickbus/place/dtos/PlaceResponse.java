package com.diel.dev.clickbus.place.dtos;

import java.time.OffsetDateTime;

public record PlaceResponse(
        Integer id,
        String name,
        String slug,
        String city,
        String state,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
