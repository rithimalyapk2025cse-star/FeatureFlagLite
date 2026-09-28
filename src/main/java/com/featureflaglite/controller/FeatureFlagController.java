package com.featureflaglite.controller;

import com.featureflaglite.dto.FeatureFlagRequest;
import com.featureflaglite.entity.FeatureFlag;
import com.featureflaglite.service.FeatureFlagService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/flags")
public class FeatureFlagController {
    private final FeatureFlagService service;

    public FeatureFlagController(FeatureFlagService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FeatureFlag> create(@Valid @RequestBody FeatureFlagRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping
    public List<FeatureFlag> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public FeatureFlag one(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public FeatureFlag update(@PathVariable Long id, @Valid @RequestBody FeatureFlagRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
