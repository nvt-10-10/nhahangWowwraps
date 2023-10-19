package com.example.demo.service;

import com.example.demo.model.entity.Ward;
import com.example.demo.reponsitory.WardReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class WardService {
    private WardReponsitory repo;

    @Autowired
    public WardService( WardReponsitory repo) {
        this.repo = repo;
    }

    public List<Ward> ListALl() {
        return (List<Ward>) repo.findAll();
    }



        public Ward FindByID(Integer ID) {
        Optional<Ward> categoryOptional = repo.findById(ID);
        return categoryOptional.orElse(null); // Trả về null nếu không tìm thấy đối tượng
    }

    public List<Ward> findByIDCity(int ID){
        return  repo.findAllByID_City(ID);
    }

}
