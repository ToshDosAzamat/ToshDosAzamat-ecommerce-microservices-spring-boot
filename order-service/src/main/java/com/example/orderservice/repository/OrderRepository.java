package com.example.orderservice.repository;

import com.example.orderservice.model.Order;
import com.example.orderservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findById(long id);
}
