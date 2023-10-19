package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Product__Review")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference  // Chỉ định mối quan hệ là "back reference"
    @JoinColumn(name = "idProduct")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference  // Chỉ định mối quan hệ là "back reference"
    @JoinColumn(name = "idReview")
    private Review review;

}
