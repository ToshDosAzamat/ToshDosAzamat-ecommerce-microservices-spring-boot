package com.example.orderservice.service;

import com.example.orderservice.dto.ProductDto;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.Product;

import java.util.*;

public interface OrderService {
    Order create(List<Long> productIdList);
    Order readbyid(long id);
    Order readbyclientname(String clientName);

    Order update(long id, List<Long> productIdList);
    List<ProductDto> readbyservice(long id);
}
