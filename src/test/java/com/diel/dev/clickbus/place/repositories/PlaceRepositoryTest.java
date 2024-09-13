package com.diel.dev.clickbus.place.repositories;

import com.diel.dev.clickbus.place.entities.Place;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;

import java.time.OffsetDateTime;
import java.util.Optional;

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
    void findBySlug_ShouldReturnSlug() {
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
    void findBySlug_ShouldNotReturnSlug_WhenSlugIsNotFound() {
        Optional<Place> retrieved = repository.findBySlug("paulista-avenue");

        assertTrue(retrieved.isEmpty());
    }
}