package com.example.productservice.repository;

import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByNameAndAndDescription(String name, String description);
    Optional<List<Product>> findByCategoriesContaining(Category category);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Product> findByQuery(String query);

    Optional<List<Product>> findByAuthorname(String authorname);

    @Override
    boolean existsById(Long id);
}
