package com.diel.dev.clickbus.place.repositories;

import com.diel.dev.clickbus.place.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
    Optional<Place> findBySlug(String slug);

}
