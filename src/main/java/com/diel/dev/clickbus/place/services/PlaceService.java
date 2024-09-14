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

    public Place update(Integer id, String slug, Place toReplace) {
        Place toUpdate = retrieve(id, slug);
        boolean isNameInUse = repository.findByName(toReplace.getName()).isPresent();
        boolean areNamesDifferent = !toUpdate.getName().equals(toReplace.getName());

        if(isNameInUse && areNamesDifferent) {
            throw new ConstraintConflictException("Place name is already in use");
        }

        String newSlug = getSlug(toReplace.getName());

        toUpdate.setName(toReplace.getName());
        toUpdate.setSlug(newSlug);
        toUpdate.setCity(toReplace.getCity());
        toUpdate.setState(toReplace.getState());
        toUpdate.setUpdatedAt(OffsetDateTime.now());

        return repository.save(toUpdate);
    }

    private String getSlug(String name) {
        return name.replace(" ", "-").toLowerCase();
    }

}
