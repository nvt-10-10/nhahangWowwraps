package com.example.demo.reponsitory;

import com.example.demo.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewReponsitory  extends CrudRepository<Review,Integer> {
    @Query("SELECT r FROM Review r ORDER BY r.id DESC")
    Page<Review> findNewestOrder_Detail(Pageable pageable);
    @Query("SELECT r FROM Review r join ProductReview  pr on r.id = pr.review.id where pr.product.id=:ID order by pr.id desc")
    List<Review> findAllByID_Product(@Param("ID") Integer ID,Pageable pageable) ;
    @Query("SELECT count(r) FROM Review r join ProductReview  pr on r.id = pr.review.id where pr.product.id=:ID order by pr.id desc")
    Integer SizeReviewByProduct(@Param("ID") Integer ID);

    @Query("SELECT sum(r.review)/count(r.id) FROM Review r join ProductReview  pr on r.id = pr.review.id where pr.product.id=:ID order by pr.id desc")
    Float star(@Param("ID") Integer ID);
    @Query ("select  r from Review r where r.email=:email order by  r.id DESC ")
    Page<Review> getReviewNew(@Param("email") String email,Pageable pageable) ;

}
