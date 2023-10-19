package com.example.demo.restcontroller;

import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Tag;
import com.example.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagAPI {
    private final TagService tagService;
    @Autowired
    public TagAPI(TagService tagService){
        this.tagService=tagService;
    }

    @GetMapping("/indexPage/{indexPage}")
    public ResponseEntity<List<Tag>> getNewChef(@PathVariable Integer indexPage){
        Pageable pageable = PageRequest.of(indexPage,10);
        List<Tag> categories = tagService.findByPage(pageable).getContent();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/Byid/{id}")
    public ResponseEntity<Tag> getChefByID(@PathVariable Integer id){
        return ResponseEntity.ok(tagService.GetByID(id));
    }

    @GetMapping("/search/{keyword}/{pageNumber}")
    public ResponseEntity<List<Tag>> getChefsByKeyWord(@PathVariable String keyword, @PathVariable int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10);
        List<Tag> categories = tagService.findByPageAndIdOrName(pageable, keyword).getContent();
        return ResponseEntity.ok(categories);
    }

}