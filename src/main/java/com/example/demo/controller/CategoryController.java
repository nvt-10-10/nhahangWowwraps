package com.example.demo.controller;

import com.example.demo.model.entity.Category;
import com.example.demo.service.CategoryService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService ;
    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }
    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> save(@ModelAttribute Category category,@RequestParam("keyword") String keyword){
        Map<String,Object> response = new HashMap<>();
        if (categoryService.checkName(category.getName())){
            categoryService.save(category);
            int pageCount = categoryService.countPage();
            if (keyword != null) {
                pageCount = categoryService.countByKeyWord(keyword);
            }
            response.put("error",false);
            if (category.getId()>0){
                response.put("mess","Add category success");
            } else {
                response.put("mess","Edit category success");
            }
          response.put("pageCount", pageCount);
        } else {
            response.put("error",true);
            response.put("mess","Name already exists");
        }
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String,Object>> delete(@RequestParam("id") int id, @RequestParam(value = "keyword", required = false) String keyword){
        System.out.println("id"+id);
        categoryService.deleteByID(id);
        Map<String,Object> reponse = new HashMap<>();
        reponse.put("error",false);
        return ResponseEntity.ok(reponse);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchChefs(@RequestParam(required = false) String keyword, HttpServletResponse response) throws UnsupportedEncodingException {
        Map<String, Object> reponse = new HashMap<>();
        int pageCount;
        if (keyword != null && keyword.length() > 0) {
            pageCount = categoryService.countByKeyWord(keyword);
        } else {
            pageCount = categoryService.countPage();
        }
        Cookie cookie = new Cookie("keyword", keyword != null ? URLEncoder.encode(keyword, "UTF-8") : "");
        cookie.setMaxAge(3600); // Thời gian sống của cookie (giây)
        cookie.setPath("/category"); // Đường dẫn sẽ có thể truy cập cookie
        response.addCookie(cookie);
        reponse.put("pageCount", pageCount);
        return ResponseEntity.ok(reponse);
    }
}
