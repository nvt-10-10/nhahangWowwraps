package com.example.demo.restcontroller;

import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Chef;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryAPI {
    private final CategoryService categoryService;
    @Autowired
    public CategoryAPI(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    @GetMapping("/indexPage/{indexPage}")
    public ResponseEntity<List<Category>> getNewChef(@PathVariable Integer indexPage){
        Pageable pageable = PageRequest.of(indexPage,10);
        List<Category> categories = categoryService.findProductByPage(pageable).getContent();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/Byid/{id}")
    public ResponseEntity<Category> getChefByID(@PathVariable Integer id){
        return ResponseEntity.ok(categoryService.GetByID(id));
    }

    @GetMapping("/search/{keyword}/{pageNumber}")
    public ResponseEntity<List<Category>> getChefsByKeyWord(@PathVariable String keyword, @PathVariable int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10);
        List<Category> categories = categoryService.findByPageAndIdOrName(pageable, keyword).getContent();
        return ResponseEntity.ok(categories);
    }

}
