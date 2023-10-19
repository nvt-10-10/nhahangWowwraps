package com.example.demo.model.data;

import jakarta.transaction.Transactional;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
public class Statistical {
    private String name;
    private String value;
}
