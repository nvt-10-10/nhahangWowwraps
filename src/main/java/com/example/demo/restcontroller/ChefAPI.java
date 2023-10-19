package com.example.demo.restcontroller;

import com.example.demo.model.entity.Chef;
import com.example.demo.service.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chef")
public class ChefAPI {
    private ChefService chefService;
    @Autowired
    public ChefAPI(ChefService chefService){
        this.chefService=chefService;
    }

    @GetMapping("/indexPage/{indexPage}")
    public List<Chef> getNewChef(@PathVariable Integer indexPage){
        Pageable pageable = PageRequest.of(indexPage,10);
        return chefService.findProductByPage(pageable).getContent();
    }
    @GetMapping("/Byid/{idChef}")
    public Chef getChefByID(@PathVariable Integer idChef){
        return  chefService.FindByID(idChef).get();
    }
    @GetMapping("/Search/{keyword}/{pageNumber}")
    public ResponseEntity<List<Chef>> getChefsByKeyWord(@PathVariable String keyword, @PathVariable int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10);
        List<Chef> chefs = chefService.findProductByPageAndIdOrName(pageable, keyword).getContent();
        return ResponseEntity.ok(chefs);
    }
}
