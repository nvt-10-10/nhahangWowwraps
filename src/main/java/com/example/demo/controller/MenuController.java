package com.example.demo.controller;

import com.example.demo.model.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MenuController {

    private ProductService productService;

    @Autowired
    public MenuController(ProductService productService) {
        this.productService = productService;
    }



}
