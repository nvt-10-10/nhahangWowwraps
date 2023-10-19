package com.example.demo.controller;


import com.example.demo.model.entity.Book;
import com.example.demo.model.entity.PriceHistory;
import com.example.demo.model.entity.Product;

import com.example.demo.service.CategoryService;
import com.example.demo.service.PriceHistoryService;
import com.example.demo.service.ProductService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("product")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;


    @Autowired
    public ProductController(ProductService productServicee,CategoryService categoryService) {
        this.productService = productServicee;
        this.categoryService=categoryService;
    }

    @CachePut("products") // Cập nhật cache sau khi thêm đối tượng
    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> addObject(
            Product product,
            @RequestParam(value = "imageFile",required = false) MultipartFile imageFile,
            @RequestParam("keyword") String keyword,
            @RequestParam("categoryID") int categoryID,
            @RequestParam("id") int id
            ,
            @RequestParam(value = "DateUpdate",required = false) String DateUpdate,
            @RequestParam(value = "linkImg",required = false) String linkImg
    ) {
        Map<String, Object> response = new HashMap<>();
        try {
            int pageCount = productService.countPage();

            if (keyword != null) {
                pageCount = productService.countByKeyWord(keyword);
            }
            product.setCategory(categoryService.GetByID(categoryID));
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageLink = saveFile(imageFile);
                product.setImageLink(imageLink); // Sử dụng đường dẫn trả về từ saveFile
            } else {
                product.setImageLink(linkImg);
                System.out.println("imageFile is empty");
            }
            productService.save(product);
            response.put("message", "Success");
            response.put("error", false);
            response.put("pageCount", pageCount);
        } catch (Exception e) {
            response.put("error", true);
            response.put("message", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @CacheEvict("products")
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteChef(@RequestParam("id") int id, @RequestParam(value = "keyword", required = false) String keyword) {
        Map<String, Object> reponse = new HashMap<>();
        System.out.println("da goi delete");

        try {
            int pageCount = productService.countPage();
            System.out.println(id);
            productService.deleteById(id);
            reponse.put("error", false);
            reponse.put("message", "Xóa thành công");
            if (keyword != null && keyword.length()>0)
                pageCount = productService.countByKeyWord(keyword);
            reponse.put("pageCount", pageCount);

        } catch (Exception e) {
            reponse.put("error", true);
            reponse.put("message", e.getMessage());
        }
        return ResponseEntity.ok(reponse);
    }



    @PostMapping("/search")
    public ResponseEntity<Map<String, Object>> searchChefs(@RequestParam(required = false) String keyword, HttpServletResponse response) throws UnsupportedEncodingException {
        Map<String, Object> resultMap = new HashMap<>();
        int pageCount;

        if (keyword != null && !keyword.isEmpty()) {
            pageCount = productService.countByKeyWord(keyword);
        } else {
            pageCount = productService.countPage();
        }

        System.out.println(keyword);
// Tạo và cài đặt cookie cho /product
        Cookie cookieForProduct = new Cookie("keyword", keyword != null ? URLEncoder.encode(keyword, "UTF-8") : "");
        cookieForProduct.setMaxAge(3600); // Thời gian sống của cookie (giây)
        cookieForProduct.setPath("/product"); // Đường dẫn có thể truy cập cookie
        response.addCookie(cookieForProduct);


        resultMap.put("pageCount", pageCount);
        return ResponseEntity.ok(resultMap);
    }

    private String saveFile(MultipartFile file) throws IOException {
        String uploadDir = "src/main/resources/static/assets/imgs/product"; // Đường dẫn thư mục lưu file
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return "/assets/imgs/product/" + fileName; // Trả về đường dẫn lưu trong database
        } catch (IOException e) {
            throw new IOException("Could not store file " + fileName, e);
        }
    }
}
