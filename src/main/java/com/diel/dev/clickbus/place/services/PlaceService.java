package com.diel.dev.clickbus.place.services;

import com.diel.dev.clickbus.place.entities.Place;
import com.diel.dev.clickbus.place.repositories.PlaceRepository;
import com.diel.dev.clickbus.shared.exceptions.ConstraintConflictException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class PlaceService {
    private final PlaceRepository repository;

    public PlaceService(PlaceRepository repository) {
        this.repository = repository;
    }

    public Place create(Place toCreate) {
        boolean isNameInUse = repository.findByName(toCreate.getName()).isPresent();

        if (isNameInUse) {
            throw new ConstraintConflictException("Place name is already in use");
        }

        String slug = getSlug(toCreate.getName());
        OffsetDateTime createdAt = OffsetDateTime.now();

        toCreate.setSlug(slug);
        toCreate.setCreatedAt(createdAt);

        return repository.save(toCreate);
    }

    private String getSlug(String name) {
        return name.replace(" ", "-").toLowerCase();
    }

}
