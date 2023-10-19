package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "Product")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 255)
    private String name;
    @Column( length = 255)
    private String description;
    @Column( length = 255)
    private String Summary;

    private int quantity;
    private String imageLink;
    private Integer meal;
    private Float price;
    private String tag;
    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date date;

    @Column(name = "special_dish")
    private boolean specialDish; // Trường món đặc biệt

    @Column(updatable = false)
    private boolean status;
    // Phương thức được chạy trước khi đối tượng được lưu vào cơ sở dữ liệu

    @ManyToOne
    @JsonBackReference
    @JsonManagedReference
    @JoinColumn(name = "idCategory")
    private Category category;



    // Sử dụng kiểu Order_Detail thay vì List<Order_Detail>
    @OneToMany (mappedBy = "product")
    @JsonIgnore
    private List<OrderDetail> orderDetail;

    @OneToMany (mappedBy = "product")
    @JsonIgnore

    private List<PriceHistory> priceHistories;

    @OneToMany (mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ProductReview> Product_Review;

    @OneToMany (mappedBy = "product")
    private List<ProductTag> productTags;

    public  int getSizeReview(){
        if(Product_Review==null)
            return  0;
        else
            return  Product_Review.size();
    }

    @JsonProperty("mealString")
    public String Meal() {
        if (meal == 1) {
            return "Breakfast";
        } else if (meal == 2) {
            return "Lunch";
        }
        else if (meal == 3) {
            return "Dinner";
        }
        return "Starters";
    }

    @JsonProperty("imageBase64")
    public String convertToBase64() throws IOException {
        File imageFile = new File("src/main/resources/static"+this.imageLink);

        if (!imageFile.exists()) {
            throw new IOException("Image file does not exist.");
        }

        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
        byte[] base64Bytes = Base64.getEncoder().encode(imageBytes);

        String category = this.imageLink.substring(this.imageLink.lastIndexOf(".") + 1);

        return "data:image/"+category+";base64," + new String(base64Bytes);
    }

    public String getFormattedPrice() {
        NumberFormat format = new DecimalFormat("$ #,##0.00");
        return format.format(price);
    }

    public Float getPriceFloat() {
        return price;
    }


    @PrePersist
    public void prePersist() {
        this.date = new Date();
        this.status=true;
    }
    @JsonProperty("getPriceOrder")

    public Float getPriceOrder(){
        int size =priceHistories.size();
        Float price = 0.0F;
        if (size>0)
             price =  priceHistories.get(size-1).getPrice();
        return  price;
    }
}
