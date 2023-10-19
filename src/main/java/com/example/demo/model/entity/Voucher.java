package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "Voucher")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true, length = 10)
    private String code;
    @Column
    private Float value;
    @Column
    private String unit;
    @Temporal(TemporalType.DATE)
    private Date date; // Trường mới để lưu trữ ngày đăng
    @Column(name = "end_date")
    private Date endDate; // Trường để lưu trữ ngày kết thúc thời gian hoạt động của Voucher
    private boolean status;

    @OneToMany(mappedBy = "voucher")
    @JsonIgnore
    private List<Order> orders;

    @PrePersist
    public void setDate() {
        this.date = new Date();
    }


    public void setEndDate(){
        this.date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.date);
        calendar.add(Calendar.MONTH, 1); // Thêm 1 tháng
        this.endDate = calendar.getTime();
    }

    public String getDateVN(Date date){
        if (date == null) {
            return ""; // Xử lý trường hợp date là null
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("vi", "VN"));
        return dateFormat.format(date);
    }
}
