package com.example.demo.model.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuItem {

    private String link;
    private String name;

    public MenuItem(String name, String link) {
        this.link = link;
        this.name = name;
    }
}
