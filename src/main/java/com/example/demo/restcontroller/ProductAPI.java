package com.example.demo.restcontroller;

import com.example.demo.model.entity.Chef;
import com.example.demo.model.entity.Product;
import com.example.demo.service.ProductService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductAPI {
    private final ProductService productService;

    @Autowired
    public ProductAPI(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{categoryId}")
    public List<Product> getAllProductMenuItems(@PathVariable Integer categoryId) {
        return productService.FindByIDCategory(categoryId);
    }

    @GetMapping("/quantity/{id}")
    public ResponseEntity<Map<String,Object>> getQuantityByID(@PathVariable Integer id) {
        Map<String,Object> reponse = new HashMap<>();
        try {
            Integer quantity = productService.getQuantityByID(id);
            System.out.println(quantity);
            reponse.put("quantity",quantity);
            return ResponseEntity.ok(reponse);
        } catch (Exception e) {
            // Xử lý ngoại lệ khác (nếu cần)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Caching
    @GetMapping("/search/{keyword}/{pageNumber}")
    public ResponseEntity<List<Product>> getChefsByKeyWord(@PathVariable String keyword, @PathVariable int pageNumber, HttpServletRequest request) {

        Pageable pageable = PageRequest.of(pageNumber, 10);
        List<Product> chefs = productService.findProductByPageAndIdOrName(pageable, keyword).getContent();
        return ResponseEntity.ok(chefs);
    }

    @Caching
    @GetMapping("/indexPage/{indexPage}")
    public List<Product> getNewChef(@PathVariable Integer indexPage){
        Pageable pageable = PageRequest.of(indexPage,10);
        return productService.findByPage(pageable).getContent();
    }
    @Caching
    @GetMapping("/Byid/{id}")
    public Product getById(@PathVariable Integer id){
        return  productService.FindByID(id);
    }

    @GetMapping("/all")
    public List<Product> getAllProductMenu() {
        return productService.ListALl();
    }

}
