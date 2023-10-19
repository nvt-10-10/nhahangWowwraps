package com.example.demo.reponsitory;

import com.example.demo.model.entity.Ward;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WardReponsitory extends CrudRepository<Ward,Integer> {
    @Query("SELECT w FROM Ward w where w.city.id=:ID ORDER BY w.id")
    List<Ward> findAllByID_City(@Param("ID") Integer ID) ;
}
