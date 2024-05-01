package com.example.paymentservice.repository;

import com.example.paymentservice.model.MyPay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPayRepository extends JpaRepository<MyPay, Long> {
}
