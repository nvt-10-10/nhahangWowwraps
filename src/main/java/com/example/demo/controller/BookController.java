package com.example.demo.controller;

import com.example.demo.model.entity.Book;
import com.example.demo.service.BookService;
import com.example.demo.service.impl.ClientServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller

public class BookController {
    private final BookService bookService;
    private final ClientServiceImpl clientService;

    public BookController(BookService bookService, ClientServiceImpl clientService) {
        this.bookService = bookService;
        this.clientService = clientService;

    }

    @PostMapping("book/save")
    public ResponseEntity<Map<String, Object>> saveBook(Book book, @RequestParam("time1") String time, @RequestParam("keyword") String keyword,HttpServletResponse httpServletResponse) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Map<String, Object> response = new HashMap<>();
        int pageCount = bookService.countPage();
        try {

            Date parsedDate = sdf.parse(time);
            Time sqlTime = new Time(parsedDate.getTime());
            book.setTime(sqlTime);
            bookService.Save(book);
            response.put("message", "Add Success");
            response.put("error", false);
            if (keyword.trim().length()>0){
                pageCount = bookService.countByKeyWord(keyword);
            }
        } catch (ParseException e) {

            e.printStackTrace();
            response.put("message", e.getMessage());
            response.put("error", true);
        }
        response.put("pageCount", pageCount);
        Cookie cookie = new Cookie("updateDataListBook", "true");
        cookie.setMaxAge(5); // Thời gian sống của cookie (giây)
        cookie.setPath("/book"); // Đường dẫn sẽ có thể truy cập cookie
        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("book/delete")
    public ResponseEntity<Map<String, Object>> deleteChef(@RequestParam("id") int id, @RequestParam(value = "keyword", required = false) String keyword) {
        Map<String, Object> reponse = new HashMap<>();
        System.out.println("da goi delete");

        try {
            int pageCount = bookService.countPage();
            System.out.println(id);
            bookService.deleteById(id);
            reponse.put("error", false);
            reponse.put("message", "Xóa thành công");
            if (keyword != null && keyword.length() > 0)
                pageCount = bookService.countByKeyWord(keyword);
            reponse.put("pageCount", pageCount);

        } catch (Exception e) {
            reponse.put("error", true);
            reponse.put("message", e.getMessage());
        }
        return ResponseEntity.ok(reponse);
    }

    @GetMapping("book/confirm")
    public ResponseEntity<Map<String, Object>> confirm(@RequestParam("id") int id, @RequestParam(value = "keyword", required = false) String keyword) {
        Map<String, Object> reponse = new HashMap<>();
        System.out.println("da goi delete");
        System.out.println("id" + id);
        try {
            int pageCount = bookService.countPage();
            System.out.println(id);
            Book book =
                    bookService.FindByID(id);
            book.setStatus(1);
            bookService.Save(book);
            reponse.put("error", false);
            reponse.put("message", "Confirm Success");
            if (keyword != null && keyword.length() > 0)
                pageCount = bookService.countByKeyWord(keyword);
            reponse.put("pageCount", pageCount);
            book.setStatus(0);
            clientService.createBook(book);
        } catch (Exception e) {
            reponse.put("error", true);
            reponse.put("message", e.getMessage());
        }
        return ResponseEntity.ok(reponse);
    }

    @PostMapping("book/cancel")
    public ResponseEntity<Map<String, Object>> cancel(@RequestParam("id") int id, @RequestParam(value = "keyword", required = false) String keyword
            , @RequestParam(value = "reason", required = false) String reason) {
        Map<String, Object> reponse = new HashMap<>();
        System.out.println("da goi delete");
        System.out.println("id" + id);
        try {
            int pageCount = bookService.countPage();
            System.out.println(id);
            Book book =
                    bookService.FindByID(id);
            book.setStatus(2);
            book.setReason(reason);
            bookService.Save(book);
            reponse.put("error", false);
            reponse.put("message", "Cancel Success");
            if (keyword != null && keyword.length() > 0)
                pageCount = bookService.countByKeyWord(keyword);
            reponse.put("pageCount", pageCount);
            clientService.createBookCancel(book);
        } catch (Exception e) {
            reponse.put("error", true);
            reponse.put("message", e.getMessage());
        }
        return ResponseEntity.ok(reponse);
    }

    @PostMapping("book/search")
    public ResponseEntity<Map<String, Object>> searchChefs(@RequestParam(required = false) String keyword, HttpServletResponse response) throws UnsupportedEncodingException {
        Map<String, Object> resultMap = new HashMap<>();
        int pageCount;

        if (keyword != null && !keyword.isEmpty()) {
            pageCount = bookService.countByKeyWord(keyword);
        } else {
            pageCount = bookService.countPage();
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
