package com.example.demo.restcontroller;

import com.example.demo.model.entity.City;
import com.example.demo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/city")
public class CityAPI {
    private final CityService cityService;

    @Autowired
    public CityAPI(CityService cityService) {
        this.cityService = cityService;
    }
    @GetMapping("/{cityID}")
    public List<City> getAllProductMenuItems(@PathVariable Integer cityID) {
        return cityService.findByIDProvince(cityID);
    }

    @GetMapping("/all")
    public List<City> getAllProductMenu() {
        return cityService.ListALl();
    }
}
