package com.example.demo.controller;

import com.example.demo.model.entity.Voucher;
import com.example.demo.service.VoucherSevice;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class VoucherController {
    private VoucherSevice voucherSevice;

    public VoucherController(VoucherSevice voucherSevice){
        this.voucherSevice= voucherSevice;
    }

    @GetMapping("/checkcode")
    public ResponseEntity<Map<String, Object>> checkCode(@RequestParam(value = "code",required = false) String code, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        System.out.println("code"+code);
        try {
            Voucher voucher = voucherSevice.checkcode(code);
//            System.out.println(voucher.g);
            if (voucher != null) {
                response.put("message", "Coupon applied successfully!");
                response.put("voucher", voucher);
                voucher.setStatus(false);
                voucherSevice.save(voucher);
            } else {
                response.put("error", true);
                response.put("message", "Invalid coupon code!");
            }
        } catch (Exception e) {
            response.put("error", true);
            response.put("message", "An error occurred while processing the coupon code.");
        }

        return ResponseEntity.ok(response);
    }


}
