package com.example.orderservice.controller;


import com.example.orderservice.dto.ProductDto;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody List<Long> productIdList){
        return new ResponseEntity<>(orderService.create(productIdList), HttpStatus.OK);
    }
    @GetMapping("/readbyid/{id}")
    public ResponseEntity<Order> readbyidorder(@PathVariable long id){
        return new ResponseEntity<>(orderService.readbyid(id), HttpStatus.OK);
    }
    @PostMapping("/readbyclientname/{clientname}")
    public ResponseEntity<Order> readbyclientnametop(@PathVariable String clientname){
        return new ResponseEntity<>(orderService.readbyclientname(clientname),HttpStatus.OK);
    }
    @PostMapping("/update/ {id}")
    public ResponseEntity<Order> updateorder(@PathVariable long id, @RequestBody List<Long> productIdList){
        return new ResponseEntity<>(orderService.update(id, productIdList), HttpStatus.OK);
    }
    @GetMapping("/readbyservice/{orderId}")
    public ResponseEntity<List<ProductDto>> readbyservicecontroller(@PathVariable long orderId){
        return new ResponseEntity<>(orderService.readbyservice(orderId), HttpStatus.OK);
    }

}
