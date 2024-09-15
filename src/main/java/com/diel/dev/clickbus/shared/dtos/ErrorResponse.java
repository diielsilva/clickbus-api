package com.diel.dev.clickbus.shared.dtos;

import java.time.OffsetDateTime;

public record ErrorResponse(
        OffsetDateTime timestamps,
        Integer status,
        String path,
        String message
) {
}
