package com.example.demo.service;


import com.example.demo.model.data.Statistical;
import com.example.demo.model.entity.Order;
import com.example.demo.reponsitory.OrderReponsitory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    private OrderReponsitory repo;

    @Autowired
    public OrderService(OrderReponsitory repo) {
        this.repo = repo;
    }

    public List<Order> ListALl() {
        return (List<Order>) repo.findAll();
    }

    public Order FindByID(Integer ID) {
        Optional<Order> categoryOptional = repo.findById(ID);
        return categoryOptional.orElse(null); // Trả về null nếu không tìm thấy đối tượng
    }

    public Order save(Order order) {
        return repo.save(order);
    }


    public Page<Order> find10OrderTotals() {
        Pageable page = PageRequest.of(0, 10);
        return repo.find10OrderTotals(page);
    }

    public Page<Order> findByPage(Pageable pageable) {
        return repo.findByPage(pageable);
    }

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+"); // Kiểm tra chuỗi có toàn bộ là số (có thể có dấu trừ ở đầu)
    }

    public Page<Order> findProductByPageAndIdOrName(Pageable pageable, String keyword) {
        if (isNumeric(keyword)) {
            return repo.findByPageAndIdOrName(pageable, Integer.parseInt(keyword), keyword);
        }
        return repo.findByPageAndIdOrName(pageable, -1, keyword);
    }

    public int size(){
        return (int)repo.size();
    }

    public int countPage() {
        int page = (int) (repo.size() / 10);
        return (repo.size() % 10 == 0 ? page : page + 1);
    }

    public int countByKeyWord(String keyword) {
        int numericValue = isNumeric(keyword) ? Integer.parseInt(keyword) : -1;
        long totalCount = repo.countByKeyWord(numericValue, keyword);
        int totalPages = (int) (totalCount / 10);
        System.out.println("totalCount: " + totalCount);
        if (totalCount % 10 != 0) {
            totalPages++;
        }
        System.out.println("totalPages: " + totalPages);

        return totalPages;
    }

    public void deleteById(int id) {
        repo.deleteById(id);
    }



}
