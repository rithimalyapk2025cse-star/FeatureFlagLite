package com.featureflaglite.controller;

import com.featureflaglite.dto.StateRequest;
import com.featureflaglite.entity.*;
import com.featureflaglite.service.FlagStateService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/flags")
public class FlagStateController {
    private final FlagStateService service;

    public FlagStateController(FlagStateService service) {
        this.service = service;
    }

    @PostMapping("/{id}/state")
    public FlagState setState(@PathVariable Long id, @RequestParam String environment,
                              @Valid @RequestBody StateRequest request) {
        return service.setState(id, environment, request);
    }

    @GetMapping("/{id}/state/{environment}")
    public FlagState getState(@PathVariable Long id, @PathVariable String environment) {
        return service.getState(id, environment);
    }

    @GetMapping("/{id}/evaluate/{environment}")
    public Map<String, Object> evaluate(@PathVariable Long id, @PathVariable String environment,
                                        @RequestParam String userId) {
        return Map.of(
                "featureId", id,
                "environment", environment.toUpperCase(),
                "userId", userId,
                "enabledForUser", service.evaluate(id, environment, userId)
        );
    }

    @GetMapping("/{id}/changes")
    public List<ChangeLog> changes(@PathVariable Long id) {
        return service.getLogs(id);
    }
}
