package com.example.demo.restcontroller;

import com.example.demo.model.entity.Province;
import com.example.demo.service.ProvinceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/province")
public class ProvinceAPI {
    private ProvinceService provinceService;

    public ProvinceAPI (ProvinceService provinceService){
        this.provinceService= provinceService;
    }

    @GetMapping("/all")
    public List<Province> getAllProvince() {
        return provinceService.ListALl();
    }
}
