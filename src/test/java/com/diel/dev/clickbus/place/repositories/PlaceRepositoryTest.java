package com.diel.dev.clickbus.place.repositories;

import com.diel.dev.clickbus.place.entities.Place;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName(value = "Place Repository Test")
class PlaceRepositoryTest {

    private static final MySQLContainer<?> MYSQL_CONNECTION = new MySQLContainer<>("mysql:9.0.1");

    @Autowired
    private PlaceRepository repository;

    @BeforeAll
    static void beforeAll() {
        MYSQL_CONNECTION.start();
    }

    @AfterAll
    static void afterAll() {
        MYSQL_CONNECTION.close();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    @DisplayName(value = "A place should be returned, when slug was found")
    void findBySlug_ShouldReturnPlace() {
        Place toCreate = new Place(
                "Paulista Avenue",
                "paulista-avenue",
                "São Paulo",
                "SP",
                OffsetDateTime.now()
        );

        Place created = repository.save(toCreate);

        Optional<Place> retrieved = repository.findBySlug(created.getSlug());

        assertTrue(retrieved.isPresent());
    }

    @Test
    @DisplayName(value = "A place should not be returned, when slug was not found")
    void findBySlug_ShouldNotReturnPlace_WhenSlugIsNotFound() {
        Optional<Place> retrieved = repository.findBySlug("paulista-avenue");

        assertTrue(retrieved.isEmpty());
    }

    @Test
    @DisplayName(value = "A place should be returned, when name was found")
    void findByName_ShouldReturnPlace() {
        Place toCreate = new Place(
                "Paulista Avenue",
                "paulista-avenue",
                "São Paulo",
                "SP",
                OffsetDateTime.now()
        );

        Place created = repository.save(toCreate);

        Optional<Place> retrieved = repository.findByName(created.getName());

        assertTrue(retrieved.isPresent());
    }

    @Test
    @DisplayName(value = "A place should not be returned, when name was not found")
    void findByName_ShouldNotReturnPlace_WhenNameWasNotFound() {
        Optional<Place> retrieved = repository.findByName("Paulista Avenue");

        assertTrue(retrieved.isEmpty());
    }

    @Test
    @DisplayName(value = "Should return places, when we have places with name containing the specified value")
    void findByNameContainingIgnoreCase_ShouldReturnPlaces() {
        List<Place> toCreate = List.of(
                new Place(
                        "Paulista Avenue",
                        "paulista-avenue",
                        "São Paulo",
                        "SP",
                        OffsetDateTime.now()
                ),
                new Place(
                        "Freedom Avenue",
                        "freedom-avenue",
                        "São Paulo",
                        "SP",
                        OffsetDateTime.now()
                )
        );

        repository.saveAll(toCreate);

        List<Place> retrieved = repository.findByNameContainingIgnoreCase("freedom");

        assertEquals(1, retrieved.size());
    }

    @Test
    void findByNameContainingIgnoreCase_ShouldNotReturnPlaces_WhenWeDoNotHavePlacesWithTheSpecifiedValue() {
        List<Place> toCreate = List.of(
                new Place(
                        "Paulista Avenue",
                        "paulista-avenue",
                        "São Paulo",
                        "SP",
                        OffsetDateTime.now()
                ),
                new Place(
                        "Freedom Avenue",
                        "freedom-avenue",
                        "São Paulo",
                        "SP",
                        OffsetDateTime.now()
                )
        );

        repository.saveAll(toCreate);

        List<Place> retrieved = repository.findByNameContainingIgnoreCase("Route 66");

        assertTrue(retrieved.isEmpty());
    }
}