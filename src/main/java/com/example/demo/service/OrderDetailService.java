package com.example.demo.service;

import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.OrderDetail;
import com.example.demo.reponsitory.OrderDetailReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {
    private OrderDetailReponsitory repo;

    @Autowired
    public OrderDetailService(OrderDetailReponsitory repo){
        this.repo =repo;
    }

    public List<OrderDetail> ListALl() {
        return (List<OrderDetail>) repo.findAll();
    }
    public List<OrderDetail> ListALlFindByIdOrder(Order order) {
        return (List<OrderDetail>) repo.findALlByID_Order(order);
    }

    public OrderDetail FindByID(Integer ID) {
        Optional<OrderDetail> categoryOptional = repo.findById(ID);
        return categoryOptional.orElse(null); // Trả về null nếu không tìm thấy đối tượng
    }

    public void AddOrder_Detail(OrderDetail Order_Detail){
        repo.save(Order_Detail);
    }

    public void UpdateCategory(OrderDetail Order_Detail){
        repo.save(Order_Detail);

    }


    public Integer SizeOrder(){
        return repo.SizeOrder_Detail();
    }
}
