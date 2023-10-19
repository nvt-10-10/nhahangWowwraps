package com.example.demo.reponsitory;

import com.example.demo.model.entity.PriceHistory;
import com.example.demo.model.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PriceHistoryReponsitory extends CrudRepository<PriceHistory, Integer> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN false ELSE true END " +
            "FROM PriceHistory p " +
            "WHERE p.product.id = :id " +
            "      AND p.price = :price " +
            "      AND p.date = (SELECT MAX(ph.date) FROM PriceHistory ph WHERE ph.product.id = :id)")
    boolean check(@Param("price") Float price, @Param("id") Integer id);
}
