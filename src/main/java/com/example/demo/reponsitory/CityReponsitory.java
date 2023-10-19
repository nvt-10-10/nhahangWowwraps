package com.example.demo.reponsitory;

import com.example.demo.model.entity.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityReponsitory extends CrudRepository<City,Integer> {
    @Query("SELECT c FROM City c where c.province.id=:ID ORDER BY c.id")
    List<City> findAllByID_Province(@Param("ID") Integer ID) ;
}
