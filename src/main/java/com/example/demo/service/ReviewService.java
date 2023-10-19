package com.example.demo.service;

import com.example.demo.model.entity.Review;
import com.example.demo.reponsitory.ReviewReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private ReviewReponsitory repo;

    @Autowired

    public ReviewService(ReviewReponsitory repo) {
        this.repo = repo;
    }

    public Review FindByID(Integer ID) {
        Optional<Review> categoryOptional = repo.findById(ID);
        return categoryOptional.orElse(null); // Trả về null nếu không tìm thấy đối tượng
    }


    public void AddReview(Review Review) {
        repo.save(Review);
    }

    public void UpdateReview(Review Review) {
        repo.save(Review);

    }

    public List<Review> find10ReviewByID_Product(int id) {
        Pageable page = PageRequest.of(0, 10);
        return repo.findAllByID_Product(id, page);
    }

    public Integer SizeReviewByProduct(int id) {
        return repo.SizeReviewByProduct(id);
    }

    public Float star(int id ){
        return  repo.star(id);
    }

    public Review getNewReview(String email){
        Pageable page = PageRequest.of(0, 1);
        return (repo.getReviewNew(email, page).getContent().get(0));
    }
}
