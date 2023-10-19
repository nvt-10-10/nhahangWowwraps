package com.example.demo.model.data;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DataMail {
    private String to;
    private String subject;
    private String content;
    private Map<String,Object> props;
}
