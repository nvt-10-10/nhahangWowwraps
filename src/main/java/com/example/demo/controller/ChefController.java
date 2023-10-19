package com.example.demo.controller;

import com.example.demo.model.entity.Chef;
import com.example.demo.service.ChefService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.CacheControl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("chef")

public class ChefController {
    private ChefService chefService;

    @Autowired
    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> addObject(
            Chef chef,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("keyword") String keyword
    ) {
        Map<String, Object> response = new HashMap<>();

        try {
            int pageCount = chefService.countPage();

            if (keyword != null) {
                pageCount = chefService.countByKeyWord(keyword);
            }
            if (!imageFile.isEmpty()) {
                String imageLink = saveFile(imageFile);
                chef.setImageLink(imageLink); // Sử dụng đường dẫn trả về từ saveFile
                chefService.save(chef);
            } else {
                System.out.println("imageFile is empty");
            }
            response.put("message", "Success");
            response.put("error", false);
            response.put("pageCount", pageCount);

        } catch (Exception e) {
            response.put("error", true);
            response.put("message", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/deleteChef")
    public ResponseEntity<Map<String, Object>> deleteChef(@RequestParam("chefid") String chefId, @RequestParam(value = "keyword", required = false) String keyword) {
        Map<String, Object> reponse = new HashMap<>();
        System.out.println("da goi delete");

        try {
            int pageCount = chefService.countPage();
            System.out.println(chefId);
            chefService.deleteById(Integer.parseInt(chefId));
            reponse.put("error", false);
            reponse.put("message", "Xóa thành công");
            if (keyword != null && keyword.length() > 0)
                pageCount = chefService.countByKeyWord(keyword);
            reponse.put("pageCount", pageCount);

        } catch (Exception e) {
            reponse.put("error", true);
            reponse.put("message", e.getMessage());
        }
        return ResponseEntity.ok(reponse);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchChefs(@RequestParam(required = false) String keyword, HttpServletResponse response) throws UnsupportedEncodingException {
        Map<String, Object> mao = new HashMap<>();
        int pageCount;
        if (keyword != null && keyword.length() > 0) {
            pageCount = chefService.countByKeyWord(keyword);
        } else {
            pageCount = chefService.countPage();
        }
        Cookie cookie = new Cookie("keyword", keyword != null ? URLEncoder.encode(keyword, "UTF-8") : "");
        cookie.setMaxAge(3600); // Thời gian sống của cookie (giây)
        cookie.setPath("/adminChef"); // Đường dẫn sẽ có thể truy cập cookie
        response.addCookie(cookie);

        mao.put("pageCount", pageCount);
        return ResponseEntity.ok(mao);

    }

    private String saveFile(MultipartFile file) throws IOException {
        String uploadDir = "src/main/resources/static/assets/imgs/chef"; // Đường dẫn thư mục lưu file
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return "/assets/imgs/chef/" + fileName; // Trả về đường dẫn lưu trong database
        } catch (IOException e) {
            throw new IOException("Could not store file " + fileName, e);
        }
    }
}
