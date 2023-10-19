package com.example.demo.controller;

import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.Review;
import com.example.demo.model.entity.Tag;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.TagService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductDetalController {
    private ProductService productService;
    private TagService tagService;

    private ReviewService reviewService;

    @Autowired
    public ProductDetalController(ProductService productService, TagService tagService, ReviewService reviewService) {
        this.productService = productService;
        this.tagService = tagService;
        this.reviewService = reviewService;
    }



}
