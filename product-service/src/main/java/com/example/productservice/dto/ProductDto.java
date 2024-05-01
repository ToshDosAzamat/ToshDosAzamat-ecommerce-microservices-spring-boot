package com.example.productservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private long productId;
    private String name;
    private int quanty;
    private double price;
}
