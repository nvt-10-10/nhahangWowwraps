package com.example.demo.controller;

import com.example.demo.model.entity.Subscribe;
import com.example.demo.service.SubscribeService;
import com.example.demo.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SubscribeController {
    private final SubscribeService subscribeService;
    private final ClientServiceImpl clientService;
    @Autowired
    public SubscribeController(SubscribeService subscribeService,ClientServiceImpl clientService){
        this.subscribeService = subscribeService;
        this.clientService=clientService;
    }
    @PostMapping("subscribe/save")
    public ResponseEntity<Map<String,Object>> save(Subscribe subscribe){
        Map<String ,Object> response = new HashMap<>();
        Subscribe subscribe1 = subscribeService.save(subscribe);

        if (subscribe1!=null){
            response.put("error",false);
            response.put("mess","Cảm ơn bạn đã đăng ký Wowwraps");
            clientService.createSubscribe(subscribe1);
        } else {
            response.put("error",false);
            response.put("mess","Email đã tồn tại");
        }


        return ResponseEntity.ok(response);
    }
}
