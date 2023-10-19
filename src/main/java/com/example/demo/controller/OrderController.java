package com.example.demo.controller;

import com.example.demo.model.entity.Book;
import com.example.demo.model.entity.Order;
import com.example.demo.service.BookService;
import com.example.demo.service.OrderService;
import com.example.demo.service.impl.ClientServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Controller
public class OrderController {
    private final OrderService orderService;
    private final ClientServiceImpl clientService;

    public OrderController(OrderService orderService, ClientServiceImpl clientService) {
        this.orderService = orderService;
        this.clientService = clientService;

    }

    @PostMapping("order/save")
    public ResponseEntity<Map<String, Object>> saveBook(Order order, @RequestParam("time1") String time, @RequestParam("keyword") String keyword) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Map<String, Object> response = new HashMap<>();
        int pageCount = orderService.countPage();
        orderService.save(order);
        response.put("message", "Add Success");
        response.put("error", false);
        if (keyword.trim().length()>0){
            pageCount = orderService.countByKeyWord(keyword);
        }
        response.put("pageCount", pageCount);

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("order/delete")
    public ResponseEntity<Map<String, Object>> deleteChef(@RequestParam("id") int id, @RequestParam(value = "keyword", required = false) String keyword) {
        Map<String, Object> reponse = new HashMap<>();
        System.out.println("da goi delete");

        try {
            int pageCount = orderService.countPage();
            System.out.println(id);
            orderService.deleteById(id);
            reponse.put("error", false);
            reponse.put("message", "Xóa thành công");
            if (keyword != null && keyword.length() > 0)
                pageCount = orderService.countByKeyWord(keyword);
            reponse.put("pageCount", pageCount);

        } catch (Exception e) {
            reponse.put("error", true);
            reponse.put("message", e.getMessage());
        }
        return ResponseEntity.ok(reponse);
    }

    @GetMapping("order/confirm")
    public ResponseEntity<Map<String, Object>> confirm(@RequestParam("id") int id, @RequestParam(value = "keyword", required = false) String keyword) {
        Map<String, Object> reponse = new HashMap<>();
        System.out.println("da goi delete");
        System.out.println("id" + id);
        try {
            int pageCount = orderService.countPage();
            System.out.println(id);
            Order order =
                    orderService.FindByID(id);
            order.setStatus(1);
            orderService.save(order);
            reponse.put("error", false);
            reponse.put("message", "Confirm Success");
            if (keyword != null && keyword.length() > 0)
                pageCount = orderService.countByKeyWord(keyword);
            reponse.put("pageCount", pageCount);
            clientService.createOrder(order);
        } catch (Exception e) {
            reponse.put("error", true);
            reponse.put("message", e.getMessage());
        }
        return ResponseEntity.ok(reponse);
    }

    @PostMapping("order/cancel")
    public ResponseEntity<Map<String, Object>> cancel(@RequestParam("id") int id, @RequestParam(value = "keyword", required = false) String keyword
            , @RequestParam(value = "reason", required = false) String reason) {
        Map<String, Object> reponse = new HashMap<>();
        System.out.println("da goi delete");
        System.out.println("id" + id);
        try {
            int pageCount = orderService.countPage();
            System.out.println(id);
            Order order =
                    orderService.FindByID(id);
            order.setStatus(2);
            order.setReason(reason);
            orderService.save(order);
            reponse.put("error", false);
            reponse.put("message", "Cancel Success");
            if (keyword != null && keyword.length() > 0)
                pageCount = orderService.countByKeyWord(keyword);
            reponse.put("pageCount", pageCount);
            clientService.createOrder(order);
        } catch (Exception e) {
            reponse.put("error", true);
            reponse.put("message", e.getMessage());
        }
        return ResponseEntity.ok(reponse);
    }

    @PostMapping("order/search")
    public ResponseEntity<Map<String, Object>> searchChefs(@RequestParam(required = false) String keyword, HttpServletResponse response) throws UnsupportedEncodingException {
        Map<String, Object> resultMap = new HashMap<>();
        int pageCount;

        if (keyword != null && !keyword.isEmpty()) {
            pageCount = orderService.countByKeyWord(keyword);
        } else {
            pageCount = orderService.countPage();
        }

        System.out.println(keyword);
// Tạo và cài đặt cookie cho /product
        Cookie cookieForProduct = new Cookie("keyword", keyword != null ? URLEncoder.encode(keyword, "UTF-8") : "");
        cookieForProduct.setMaxAge(3600); // Thời gian sống của cookie (giây)
        cookieForProduct.setPath("/book"); // Đường dẫn có thể truy cập cookie
        response.addCookie(cookieForProduct);


        resultMap.put("pageCount", pageCount);
        return ResponseEntity.ok(resultMap);
    }
}
