package com.example.demo.service;

import com.example.demo.model.entity.Subscribe;
import com.example.demo.reponsitory.SubscribeReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribeService {
    private SubscribeReponsitory repo;

    @Autowired
    public SubscribeService(SubscribeReponsitory repo) {
        this.repo = repo;
    }

    public Boolean checkMail(String email) {
        int count = repo.checkEmail(email);
        if (count == 0)
            return true;
        return false;
    }


    public Subscribe save(Subscribe subscribe) {
        if (checkMail(subscribe.getEmail())) {
            return repo.save(subscribe);
        } else
            return null;
    }

    public List<Subscribe> findAll() {
        return (List<Subscribe>) repo.findAll();
    }
}
