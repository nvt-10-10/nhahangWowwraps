package com.example.demo.restcontroller;

import com.example.demo.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscrib")
public class SubscribeAPI {
    private final SubscribeService subscribeService;
    @Autowired
    public SubscribeAPI(SubscribeService subscribeService){
        this.subscribeService=subscribeService;
    }
    @GetMapping ("/checkmail/{email}")
    public ResponseEntity<Boolean> checkEmail(@PathVariable String email){
        return ResponseEntity.ok(subscribeService.checkMail(email));
    }
}
