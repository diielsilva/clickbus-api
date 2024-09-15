package com.diel.dev.clickbus.place.adapters;

import com.diel.dev.clickbus.place.dtos.PlaceRequest;
import com.diel.dev.clickbus.place.dtos.PlaceResponse;
import com.diel.dev.clickbus.place.entities.Place;
import org.springframework.stereotype.Component;

@Component
public class PlaceAdapter {

    public Place toEntity(PlaceRequest body) {
        return new Place(
                body.name(),
                body.city(),
                body.state()
        );
    }

    public PlaceResponse toDTO(Place toSend) {
        return new PlaceResponse(
                toSend.getId(),
                toSend.getName(),
                toSend.getSlug(),
                toSend.getCity(),
                toSend.getState(),
                toSend.getCreatedAt(),
                toSend.getUpdatedAt()
        );
    }

}
