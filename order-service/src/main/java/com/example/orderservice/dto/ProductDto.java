package com.example.orderservice.dto;

import lombok.Data;

@Data
public class ProductDto {
    private long productId;
    private String name;
    private int quanty;
    private double price;
}
