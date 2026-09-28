package com.featureflaglite.controller;

import com.featureflaglite.service.FeatureFlagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private final FeatureFlagService service;

    public DashboardController(FeatureFlagService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("flags", service.findAll());
        return "dashboard";
    }
}
