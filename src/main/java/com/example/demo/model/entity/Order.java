package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Entity
@Table(name = "orders") // Đổi tên bảng thành "orders" thay vì "order" để tránh trùng với từ khóa SQL
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 45)
    private String firstName;
    @Column(nullable = false, length = 45)
    private String lastName;
    @Column(nullable = false, length = 255)
    private String email;
    @Column(nullable = false, length = 255)
    private String companyName;

    @Column(nullable = false, length = 12)
    private String phone;

    @Column(nullable = false, length = 255)
    private String address;

    private int paymentMethods;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "UTC")
    private Date date;


    private double intoMoney;

    private String message;

    private String reason;

    private int status;

    @ManyToOne
    @JoinColumn(name = "idVoucher")
    @JsonBackReference
    private Voucher voucher;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<OrderDetail> orderDetails;

    @PrePersist
    public void prePersist() {
        this.date = new Date();
        this.status = 0;
    }


    public String getDateOrder() {
        if (date != null) {
            // Tạo đối tượng SimpleDateFormat để định dạng thời gian
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            // Đặt múi giờ cho SimpleDateFormat là múi giờ của Việt Nam
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
            return sdf.format(date);
        } else {
            return "";
        }
    }

    @JsonProperty("name")
    public String name() {
        return lastName + " " + firstName;
    }

    @JsonProperty("dishList")
    public String
    dishList() {
        String list = "";
        for (OrderDetail item : orderDetails) {
            list += item.getProduct().getName() + " x " + item.getQuantity() + ", ";
        }
        return list;
    }

    @JsonProperty("PaymentMethodsString")
    public String getPaymentMethodsString() {
        String str = "";
        if (paymentMethods == 1)
            str = "Direct bank transfer";
        else if (paymentMethods == 2)
            str = "Check payments";
        else if (paymentMethods == 3)
            str = "Cash on delivery";
        else if (paymentMethods == 4)
            str = "PayPal ";
        return str;
    }

    @JsonProperty("total_amount")
    public Float total_amount() {
        Float price = 0.0F;
        if (orderDetails.size() > 0)
            for (OrderDetail item : orderDetails
            ) {
                price += item.getPrice();
            }
        return price;
    }

    @JsonProperty("voucherFloat")
    public Float voucherFloat() {
        Float price = 0.0F;
        if (voucher!=null)
        if (voucher.getUnit().equals("%")) {
            price = total_amount() * voucher.getValue() / 100;
        } else
            price = voucher.getValue();
        return price;
    }
}
