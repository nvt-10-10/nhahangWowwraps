package com.example.demo.service;

import com.example.demo.model.entity.City;
import com.example.demo.reponsitory.CityReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CityService {
    private CityReponsitory repo;
    @Autowired
    public CityService( CityReponsitory repo) {
        this.repo = repo;
    }
    public List<City> ListALl() {
        return (List<City>) repo.findAll();
    }
    public City FindByID(Integer ID) {
        Optional<City> categoryOptional = repo.findById(ID);
        return categoryOptional.orElse(null); // Trả về null nếu không tìm thấy đối tượng
    }
    public List<City> findByIDProvince(int ID){
        return  repo.findAllByID_Province(ID);
    }

}
