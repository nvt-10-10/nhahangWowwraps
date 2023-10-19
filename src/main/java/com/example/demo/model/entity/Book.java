package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String phone;
    private String email;
    private int numberPerson;
    private String message;
    private String reason;
    @Temporal(TemporalType.TIME)
    private Time time; // Sử dụng TemporalType.TIME cho trường thời gian

    @Temporal(TemporalType.DATE)
    private Date date; // Sử dụng TemporalType.DATE cho trường ngày

    public int status;

    public String getStatusString() {
        String status = "";
        if (this.status == 0) {
            status = "Đang chờ phê duyệt";
        } else if (this.status == 1) {
            status = "Đã duyệt";
        } else {
            status = "Hủy";
        }
        return status;
    }

    public String getDateFormat() {
        // Tạo đối tượng SimpleDateFormat với ngôn ngữ tiếng Việt và định dạng ngày tháng
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", new Locale("vi", "VN"));

        // Định dạng ngày và giờ
        String formattedDate = dateFormat.format(this.date);

        return formattedDate;
    }
    @JsonProperty("dateFormat")
    public String DateFormat() {
        // Sử dụng "MM" thay vì "mm" để biểu diễn tháng
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String formattedDate = dateFormat.format(this.date);

        return formattedDate;
    }
    @JsonProperty("formattedTime")
    public String formattedTime() {
        // Tạo đối tượng SimpleDateFormat để định dạng thời gian
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", new Locale("vi", "VN"));

        // Định dạng thời gian từ đối tượng Time
        String formattedTime = timeFormat.format(this.time);

        return formattedTime;
    }

}
