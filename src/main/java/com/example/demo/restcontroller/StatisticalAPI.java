package com.example.demo.restcontroller;

import com.example.demo.model.data.Statistical;
import com.example.demo.service.StatisticalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/statistical")

public class StatisticalAPI {

    private final StatisticalService statisticalService;

    public StatisticalAPI( StatisticalService statisticalService){
        this.statisticalService=statisticalService;
    }

    @GetMapping("/order/month")
    public List<Statistical> getListOrder(){
        return   statisticalService.getTotalRevenueByMonth();
    }

    @GetMapping("/book/month")
    public List<Statistical> getListBook(){
        return   statisticalService.getTotalBookByMonth();
    }


    @GetMapping("/order/year")
    public List<Statistical> getTotalpie(){
        return   statisticalService.getOrderByYear();
    }
    @GetMapping("/book/year")
    public List<Statistical> getBookpie(){
        return   statisticalService.getBookByYear();
    }
}
