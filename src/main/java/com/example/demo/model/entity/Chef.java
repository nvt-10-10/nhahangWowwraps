package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Chef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @Column(nullable = false,length = 45)
    private String name;
    private String imageLink;
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


}
