package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class ProductTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @ManyToOne
    @JsonBackReference // Đánh dấu mối quan hệ này là "back reference"
    @JoinColumn(name = "idProduct")
    private  Product product;
    @ManyToOne
    @JsonBackReference // Đánh dấu mối quan hệ này là "back reference"
    @JoinColumn(name = "idTag")
    private Tag tag;

}
