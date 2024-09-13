package com.diel.dev.clickbus.place.services;

import com.diel.dev.clickbus.place.entities.Place;
import com.diel.dev.clickbus.place.repositories.PlaceRepository;
import com.diel.dev.clickbus.shared.exceptions.ConstraintConflictException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName(value = "Place Service Tests")
class PlaceServiceTest {

    private static final MySQLContainer<?> MYSQL_CONNECTION = new MySQLContainer<>("mysql:9.0.1");

    @Autowired
    private PlaceService service;

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
    @DisplayName(value = "A place should be created")
    void create_ShouldCreatePlace() {
        Place toCreate = new Place(
                "Paulista Avenue",
                "São Paulo",
                "SP"
        );

        Place created = service.create(toCreate);

        assertAll(() -> {
            assertEquals("paulista-avenue", created.getSlug());
            assertNotNull(created.getId());
            assertNotNull(created.getCreatedAt());
        });
    }

    @Test
    @DisplayName(value = "A place should not be created, when we already have a place with same name")
    void create_ShouldNotCreatePlace_WhenNameIsInUse() {
        Place toCreate = new Place(
                "Paulista Avenue",
                "paulista-avenue",
                "São Paulo",
                "SP",
                OffsetDateTime.now()
        );

        repository.save(toCreate);

        assertThrows(ConstraintConflictException.class, () -> service.create(toCreate));
    }

}