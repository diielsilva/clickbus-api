package com.diel.dev.clickbus.place.repositories;

import com.diel.dev.clickbus.place.entities.Place;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PlaceRepositoryTest {

    private static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:9.0.1");

    @Autowired
    private PlaceRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void findBySlug_ShouldReturnSlug() {
        Place toCreate = new Place(
                "Paulista Avenue",
                "paulista-avenue",
                "São Paulo",
                "SP",
                OffsetDateTime.now()
        );

        Place created = repository.save(toCreate);

        Optional<Place> retrieved = repository.findBySlug(toCreate.getSlug());

        assertTrue(retrieved.isPresent());
    }
}