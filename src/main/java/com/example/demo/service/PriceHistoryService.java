package com.example.demo.service;

import com.example.demo.model.entity.PriceHistory;
import com.example.demo.reponsitory.PriceHistoryReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceHistoryService {
    private PriceHistoryReponsitory repo;
    @Autowired
    public PriceHistoryService(PriceHistoryReponsitory repo){
        this.repo=repo;
    }

    public void save(PriceHistory priceHistory){
        if (repo.check(priceHistory.getPrice(),priceHistory.getProduct().getId())){
            repo.save(priceHistory);
        }
    }


}
