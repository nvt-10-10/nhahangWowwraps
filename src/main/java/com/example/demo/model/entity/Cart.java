package com.example.demo.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.text.NumberFormat;

@Setter
@Getter
@NoArgsConstructor
public class Cart {
    private Product product;
    private int quantity;
    public  Float getSubtotal(){
        return  quantity * product.getPriceFloat();
    }

    public String getSubtotalString(){
        NumberFormat format = new DecimalFormat("$ #,##0.00");

        return  format.format(getSubtotal());
    }

}
