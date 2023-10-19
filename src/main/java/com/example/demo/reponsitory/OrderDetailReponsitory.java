package com.example.demo.reponsitory;

import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailReponsitory extends CrudRepository<OrderDetail,Integer> {
    @Query("SELECT o FROM OrderDetail o ORDER BY o.id DESC")
    Page<OrderDetail> findNewestOrder_Detail(Pageable pageable);
    @Query("SELECT o FROM OrderDetail o where o.order=:order ORDER BY o.id DESC")
    List<OrderDetail> findALlByID_Order(@Param("order") Order order) ;
    @Query("SELECT count(o) FROM OrderDetail o ")
    Integer SizeOrder_Detail();
}
