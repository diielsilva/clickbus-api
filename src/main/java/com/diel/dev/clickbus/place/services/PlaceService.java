package com.diel.dev.clickbus.place.services;

import com.diel.dev.clickbus.place.entities.Place;
import com.diel.dev.clickbus.place.repositories.PlaceRepository;
import com.diel.dev.clickbus.shared.exceptions.ConstraintConflictException;
import com.diel.dev.clickbus.shared.exceptions.EntityNotFoundException;
import com.diel.dev.clickbus.shared.exceptions.QueryParameterNotSpecifiedException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

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

    public List<Place> retrieveAll(String name) {
        boolean isNameNotSpecified = name == null || name.isEmpty() || name.isBlank();

        if (isNameNotSpecified) {
            return repository.findAll();
        }

        return repository.findByNameContainingIgnoreCase(name);
    }

    public Place retrieve(Integer id, String slug) {
        boolean isIdPresent = id != null;
        boolean isSlugPresent = slug != null && !slug.isEmpty() && !slug.isBlank();

        if (isIdPresent) {
            return repository
                    .findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Place not found"));
        }

        if (isSlugPresent) {
            return repository
                    .findBySlug(slug)
                    .orElseThrow(() -> new EntityNotFoundException("Place not found"));
        }

        throw new QueryParameterNotSpecifiedException("Missing query parameters, you must specify an id or a slug");
    }

    private String getSlug(String name) {
        return name.replace(" ", "-").toLowerCase();
    }

}
