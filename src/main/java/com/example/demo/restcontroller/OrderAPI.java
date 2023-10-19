package com.example.demo.restcontroller;



import com.example.demo.model.data.Statistical;
import com.example.demo.model.entity.Order;

import com.example.demo.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.Month;
import java.util.List;
import java.util.Map;


@RequestMapping("api/order")
@RestController
public class OrderAPI {
    private final OrderService orderService;
    @Autowired
    public OrderAPI(OrderService orderService
    ) {
        this.orderService = orderService;

    }

    @GetMapping("/Byid/{id}")
    public Order getProductByOrderId(@PathVariable Integer id){
        return orderService.FindByID(id);
    }

    @GetMapping("/all")
    public List<Order> getProductByOrderALL(@PathVariable Integer id){
        Pageable page = PageRequest.of(0,10);
        return (List<Order>) orderService.findByPage(page);
    }

    @GetMapping("/search/{keyword}/{pageNumber}")
    public ResponseEntity<List<Order>> getChefsByKeyWord(@PathVariable String keyword, @PathVariable int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10);
        List<Order> chefs = orderService.findProductByPageAndIdOrName(pageable, keyword).getContent();
        return ResponseEntity.ok(chefs);
    }


    @GetMapping("/indexPage/{indexPage}")
    public List<Order> getNewChef(@PathVariable Integer indexPage){
        Pageable pageable = PageRequest.of(indexPage,10);
        return orderService.findByPage(pageable).getContent();
    }


}
