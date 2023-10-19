package com.example.demo.controller;

import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Tag;
import com.example.demo.service.CategoryService;
import com.example.demo.service.TagService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("tag")
public class TagController {
    private final TagService tagService ;
    @Autowired
    public TagController(TagService tagService){
        this.tagService=tagService;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> save(@ModelAttribute Tag tag, @RequestParam("keyword") String keyword){
        Map<String,Object> response = new HashMap<>();
        if (tagService.checkName(tag.getName())){
            tagService.save(tag);
            int pageCount = tagService.countPage();
            if (keyword != null) {
                pageCount = tagService.countByKeyWord(keyword);
            }
            response.put("error",false);
            if (tag.getId()>0){
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
        tagService.deleteByID(id);
        Map<String,Object> response = new HashMap<>();
        response.put("error",false);
        int pageCount = tagService.countPage();
        if (keyword != null) {
            pageCount = tagService.countByKeyWord(keyword);
        }
        response.put("pageCount", pageCount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchChefs(@RequestParam(required = false) String keyword, HttpServletResponse response) {
        Map<String, Object> reponse = new HashMap<>();
        int pageCount;
        if (keyword != null && keyword.length() > 0) {
            pageCount = tagService.countByKeyWord(keyword);
            Cookie cookie = new Cookie("keyword", keyword);
            cookie.setMaxAge(3600); // Thời gian sống của cookie (giây)
            cookie.setPath("/tag"); // Đường dẫn sẽ có thể truy cập cookie
            response.addCookie(cookie);
        } else {
            pageCount = tagService.countPage();
            Cookie cookie = new Cookie("keyword", null);
            cookie.setMaxAge(3600); // Thời gian sống của cookie (giây)
            cookie.setPath("/tag"); // Đường dẫn sẽ có thể truy cập cookie
            response.addCookie(cookie);
        }
        reponse.put("pageCount", pageCount);
        return ResponseEntity.ok(reponse);
    }

}
