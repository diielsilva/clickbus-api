package com.diel.dev.clickbus.place.controllers;

import com.diel.dev.clickbus.place.adapters.PlaceAdapter;
import com.diel.dev.clickbus.place.dtos.PlaceRequest;
import com.diel.dev.clickbus.place.dtos.PlaceResponse;
import com.diel.dev.clickbus.place.entities.Place;
import com.diel.dev.clickbus.place.services.PlaceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/places")
public class PlaceController {
    private final PlaceService service;
    private final PlaceAdapter adapter;

    public PlaceController(PlaceService service, PlaceAdapter adapter) {
        this.service = service;
        this.adapter = adapter;
    }

    @PostMapping
    public ResponseEntity<PlaceResponse> create(@RequestBody @Valid PlaceRequest requestBody) {
        Place toCreate = adapter.toEntity(requestBody);
        Place created = service.create(toCreate);
        PlaceResponse responseBody = adapter.toDTO(created);

        return ResponseEntity.status(CREATED).body(responseBody);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<PlaceResponse>> retrieveAll(@RequestParam(required = false) String name) {
        List<Place> retrieved = service.retrieveAll(name);
        List<PlaceResponse> responseBody = retrieved.stream().map(adapter::toDTO).toList();

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping
    public ResponseEntity<PlaceResponse> retrieve(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String slug
    ) {
        Place retrieved = service.retrieve(id, slug);
        PlaceResponse responseBody = adapter.toDTO(retrieved);

        return ResponseEntity.ok(responseBody);
    }

    @PutMapping
    public ResponseEntity<PlaceResponse> update(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String slug,
            @RequestBody @Valid PlaceRequest requestBody
    ) {
        Place toReplace = adapter.toEntity(requestBody);
        Place updated = service.update(id, slug, toReplace);
        PlaceResponse responseBody = adapter.toDTO(updated);

        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping
    public ResponseEntity<Void> remove(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String slug
    ) {
        service.remove(id, slug);

        return ResponseEntity.noContent().build();
    }

}
