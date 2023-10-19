package com.example.demo.restcontroller;

import com.example.demo.model.entity.Book;
import com.example.demo.model.entity.Product;
import com.example.demo.service.BookService;
import com.example.demo.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("api/book")
@RestController
public class BookAPI {
    private final BookService bookService;


    @Autowired
    public BookAPI(BookService bookService,ClientServiceImpl clientService) {
        this.bookService = bookService;

    }

    @GetMapping("/search/{keyword}/{pageNumber}")
    public ResponseEntity<List<Book>> getChefsByKeyWord(@PathVariable String keyword, @PathVariable int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10);
        List<Book> chefs = bookService.findProductByPageAndIdOrName(pageable, keyword).getContent();
        return ResponseEntity.ok(chefs);
    }


    @GetMapping("/indexPage/{indexPage}")
    public List<Book> getNewChef(@PathVariable Integer indexPage){
        Pageable pageable = PageRequest.of(indexPage,10);
        return bookService.findByPage(pageable).getContent();
    }
    @Caching
    @GetMapping("/Byid/{id}")
    public Book getById(@PathVariable Integer id){
        return  bookService.FindByID(id);
    }

}
