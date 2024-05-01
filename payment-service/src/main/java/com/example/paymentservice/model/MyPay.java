package com.example.paymentservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MY_PAYMENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double price;
    private String currency;
    private String method;
    private String intent;
    private String description;
}
