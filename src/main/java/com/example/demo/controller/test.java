//package com.example.demo.controller;
//
//import com.example.demo.model.entity.Order;
//import com.example.demo.service.ClientService;
//import com.example.demo.service.MailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//@Controller
//@RestController
//@RequestMapping("/client1")
//public class test {
//    @Autowired
//    ClientService clientService ;
//    @GetMapping
//            ("/create")
//    public ResponseEntity<Boolean> createResponseEntity(){
//        Order order = new Order();
//        order.setEmail("nvtuyen10102002@gmail.com");
//        return ResponseEntity.ok(clientService.create(order));
//    }
//
//
//}
