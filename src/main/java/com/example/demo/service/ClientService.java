package com.example.demo.service;

import com.example.demo.model.entity.Book;
import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.Subscribe;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@EnableAsync(proxyTargetClass = true)
public interface ClientService {
    @Async
    void createOrder(Order order);
    @Async
    void createSubscribe(Subscribe subscribe);
    @Async
    void createBook(Book book);
    @Async
    void createBookCancel(Book book);
}
