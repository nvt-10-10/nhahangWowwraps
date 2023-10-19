package com.example.demo.reponsitory;

import com.example.demo.model.entity.Subscribe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface  SubscribeReponsitory  extends CrudRepository<Subscribe,Integer> {
    @Query("select count(s) from Subscribe  s where  s.email=:email")
    Integer checkEmail(@Param("email") String email);
}
