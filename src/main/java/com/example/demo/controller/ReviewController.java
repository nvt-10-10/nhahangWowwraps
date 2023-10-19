package com.example.demo.controller;

import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.ProductReview;
import com.example.demo.model.entity.Review;
import com.example.demo.service.ProductReviewService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReviewController {
    private final  String session__key="userReview";
    private ReviewService reviewService ;
    private ProductService productService;

    private ProductReviewService productReviewService;

    @Autowired
    public ReviewController(ReviewService reviewService, ProductService productService,ProductReviewService productReviewService){
        this.reviewService = reviewService;
        this.productService=productService;
        this.productReviewService=productReviewService;
    }

    @PostMapping("/addReview")
    public ResponseEntity<Map<String, Object>> add(Review review, @RequestParam("idProduct") String idProduct,
                                                   @RequestParam("saveReview") boolean saveReview, HttpSession session){
        Map<String, Object> Response = new HashMap<>();
        if (idProduct.length()==0||idProduct==null){
            Response.put("Error","Product khong ton tai");
        } else {
            int id = Integer.parseInt(idProduct);
            Product product = productService.FindByID(id);
            reviewService.AddReview(review);
            Review review1 = reviewService.getNewReview(review.getEmail());
            ProductReview productReview = new ProductReview();
            productReview.setReview(review1);
            productReview.setProduct(product);
            productReviewService.AddProduct_Review(productReview);

            List<Review> reviews = reviewService.find10ReviewByID_Product(id);
            Response.put("reviews",reviews);
        }
        System.out.println(saveReview);

        if (saveReview) {
            review.setMessage("");
            session.setAttribute(session__key, review);
            session.setMaxInactiveInterval(60 * 60 * 24 * 31 * 3);
        } else {
            session.removeAttribute(session__key); // Xóa dữ liệu khỏi session nếu saveReview là false
        }


        return ResponseEntity.ok(Response);
    }
}
