package com.example.orderservice.service.impelement;


import com.example.orderservice.dto.ProductDto;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.Product;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.repository.ProductRepository;
import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpelement implements OrderService {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private RestTemplate restTemplate;

    @Value("${product.url}")
    private String productUrl;


    public OrderServiceImpelement(OrderRepository orderRepository, ProductRepository productRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Order create(List<Long> productIdList) {
        if(productIdList == null || productIdList.isEmpty()){
            throw new RuntimeException("Please fill in product list!");
        }
        List<Product> productList = new ArrayList<>();
        int totalQuety = 0;
        double totalPrice=0;
        for(long productId: productIdList){
            ResponseEntity<ProductDto> responseEntity = restTemplate.getForEntity(productUrl+productId, ProductDto.class);
            if(responseEntity.getStatusCode() != HttpStatus.OK){
                throw new RuntimeException("Product not found!");
            }
            ProductDto productDto = responseEntity.getBody();
            Product product = Product.builder()
                    .productId(productDto.getProductId())
                    .name(productDto.getName())
                    .quanty(productDto.getQuanty())
                    .price(productDto.getQuanty())
                    .build();
            if(productRepository.existsByProductId(product.getProductId())){
                productRepository.save(product);
                productList.add(product);
                totalQuety += product.getQuanty();
                totalPrice += product.getPrice();
            } else {
                productList.add(product);
                totalQuety += product.getQuanty();
                totalPrice += product.getPrice();
            }

        }
        Order order = Order.builder()
                .clientName("anonums")
                .totalprice(totalPrice)
                .date(new Date())
                .totalquanty(totalQuety)
                .products(productList)
                .build();
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order readbyid(long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Order not found!")
        );
        return order;
    }

    @Override
    public Order readbyclientname(String clientname) {
        return null;
    }

    @Override
    public Order update(long id, List<Long> productIdList) {
        if(productIdList == null || productIdList.isEmpty()){
            throw new RuntimeException("Please fill in product list!");
        }
        Order order = orderRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Order not found!")
        );
        List<Product> productList = new ArrayList<>();
        int totalQuety = 0;
        double totalPrice=0;
        for(long productId: productIdList){
            ResponseEntity<ProductDto> responseEntity = restTemplate.getForEntity(productUrl+productId, ProductDto.class);
            if(responseEntity.getStatusCode() != HttpStatus.OK){
                throw new RuntimeException("Product not found!");
            }
            ProductDto productDto = responseEntity.getBody();
            Product product = Product.builder()
                    .productId(productDto.getProductId())
                    .name(productDto.getName())
                    .quanty(productDto.getQuanty())
                    .price(productDto.getQuanty())
                    .build();
            if(productRepository.existsByProductId(product.getProductId())){
                productRepository.save(product);
                productList.add(product);
                totalQuety += product.getQuanty();
                totalPrice += product.getPrice();
            } else {
                productList.add(product);
                totalQuety += product.getQuanty();
                totalPrice += product.getPrice();
            }

        }

        order.setClientName("anonums");
        order.setTotalprice(totalPrice);
        order.setDate(new Date());
        order.setTotalquanty(totalQuety);
        order.setProducts(productList);
        orderRepository.save(order);
        return order;
    }
    @Override
    public List<ProductDto> readbyservice(long id){
        Order order = orderRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Order not found!")
        );
        List<Product> products = order.getProducts();
        List<ProductDto> productDtoList = new ArrayList<>();
        for(Product product: products){
            ProductDto productDto = new ProductDto();
            productDto.setProductId(product.getProductId());
            productDto.setName(product.getName());
            productDto.setPrice(product.getPrice());
            productDto.setQuanty(product.getQuanty());
            productDtoList.add(productDto);
        }
        return productDtoList;
    }
}
