package com.example.demo.service;

import com.example.demo.model.entity.Province;
import com.example.demo.reponsitory.ProvinceReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProvinceService {
    private ProvinceReponsitory repo;

    @Autowired
    public ProvinceService( ProvinceReponsitory repo) {
        this.repo = repo;
    }

    public List<Province> ListALl() {
        return (List<Province>) repo.findAll();
    }



    public Province FindByID(Integer ID) {
        Optional<Province> categoryOptional = repo.findById(ID);
        return categoryOptional.orElse(null); // Trả về null nếu không tìm thấy đối tượng
    }
}
