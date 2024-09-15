package com.diel.dev.clickbus.place.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PlaceRequest(
        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "City is mandatory")
        String city,

        @NotBlank(message = "State is mandatory")
        @Size(min = 2, max = 2, message = "State must contains two digits")
        String state
) {
}
