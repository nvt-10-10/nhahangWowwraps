package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "Review")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name ;
    @Column(nullable = false)
    private String email ;
    @Column(nullable = false)
    private String website ;
    @Column(nullable = false)
    private String message ;
    @Column(nullable = false)
    private int review ;
    @Temporal(TemporalType.DATE)
    private Date date;
    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
    @JsonIgnore  // Loại bỏ việc chuyển đổi thành JSON cho Product_Reviews
    private List<ProductReview> Product_Reviews;
    @PrePersist
    public void prePersist() {
        this.date = new Date();
    }
    public String getDate(){
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        String formattedDate = outputFormat.format(date);
        return formattedDate;
    }

}
