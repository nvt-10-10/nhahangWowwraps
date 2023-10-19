package com.example.demo.restcontroller;

import com.example.demo.model.entity.Ward;
import com.example.demo.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ward")

public class WardAPI {
    private final WardService wardService;

    @Autowired
    public WardAPI(WardService wardService) {
        this.wardService = wardService;
    }

    @GetMapping("/{wardId}")
    public List<Ward> getAllProductMenuItems(@PathVariable Integer wardId) {
        return wardService.findByIDCity(wardId);
    }

    @GetMapping("/all")
    public List<Ward> getAllProductMenu() {
        return wardService.ListALl();
    }
}
