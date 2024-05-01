package com.example.productservice.service;

import com.example.productservice.dto.ProductDto;
import com.example.productservice.model.Product;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

public interface ProductService {
    Product create(String name, String description, int quanty, double price, String category, MultipartFile file);
    List<Product> readByCategory(String category);
    List<Product> readByQuery(String query);
    List<Product> readAll();
    Product readById(long id);
    List<Product> readByAuthor();
    Product update(long id, String name, String description, int quanty, double price, String category, MultipartFile file);
    String deleteById(long id);
    ProductDto readByIdForService(long id);
}
