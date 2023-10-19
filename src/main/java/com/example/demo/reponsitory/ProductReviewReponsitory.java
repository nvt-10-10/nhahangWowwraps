package com.example.demo.reponsitory;

import com.example.demo.model.entity.ProductReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductReviewReponsitory extends CrudRepository<ProductReview,Integer> {
    @Query("SELECT p FROM ProductReview p ORDER BY p.id DESC")
    Page<ProductReview> findNewestProduct_Review(Pageable pageable);
    @Query("SELECT count(p) FROM ProductReview p ")
    Integer SizeProduct_Review();
}
