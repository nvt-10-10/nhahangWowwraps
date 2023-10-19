package com.example.demo.service;

import com.example.demo.model.entity.ProductReview;
import com.example.demo.reponsitory.ProductReviewReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductReviewService {

    private ProductReviewReponsitory repo;
    @Autowired
    public ProductReviewService(ProductReviewReponsitory repo){
        this.repo=repo;

    }

    public List<ProductReview> ListALl() {
        return (List<ProductReview>) repo.findAll();
    }

    public ProductReview FindByID(Integer ID) {
        Optional<ProductReview> categoryOptional = repo.findById(ID);
        return categoryOptional.orElse(null); // Trả về null nếu không tìm thấy đối tượng
    }

    public void AddProduct_Review(ProductReview Product_Review){
        repo.save(Product_Review);
    }

    public void UpdateProduct_Review(ProductReview Product_Review){
        repo.save(Product_Review);
    }
    public Page<ProductReview> findNewestCategories(Pageable pageable) {
        return repo.findNewestProduct_Review(pageable);
    }

    public Integer SizeProduct_Review(){
        return repo.SizeProduct_Review();
    }
}
