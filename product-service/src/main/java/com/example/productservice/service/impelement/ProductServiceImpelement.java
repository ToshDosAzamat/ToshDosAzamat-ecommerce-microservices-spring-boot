package com.example.productservice.service.impelement;


import com.example.productservice.dto.ProductDto;
import com.example.productservice.model.Category;
import com.example.productservice.model.Image;
import com.example.productservice.model.Product;
import com.example.productservice.repository.CategoryRepository;
import com.example.productservice.repository.ImageRepository;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.service.ProductService;
import com.example.productservice.service.UploadImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class ProductServiceImpelement implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ImageRepository imageRepository;
    private UploadImageService uploadImageService;

    public ProductServiceImpelement(ProductRepository productRepository,
                                    CategoryRepository categoryRepository,
                                    ImageRepository imageRepository,
                                    UploadImageService uploadImageService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.uploadImageService = uploadImageService;
    }



    @Override
    public Product create(String name, String description, int quanty, double price, String category, MultipartFile file) {
        if(productRepository.findByNameAndAndDescription(name, description).isPresent()){
            throw new RuntimeException("Product already register!");
        }

        String path_image = uploadImageService.saveImage(file);
        Image image = Image.builder()
                .imagePath(path_image)
                .build();
        imageRepository.save(image);
        Set<Image> imageSet = new HashSet<>();
        imageSet.add(image);

        Set<Category> categorySet = new HashSet<>();
        Category category1 = categoryRepository.findByCategory(category);
        if(category1 == null){
            Category newCategory = Category.builder().category(category).build();
            categoryRepository.save(newCategory);
            categorySet.add(newCategory);
        } else {
            categorySet.add(category1);
        }

        Product product = Product.builder()
                .name(name)
                .description(description)
                .quanty(quanty)
                .price(price)
                .authorname("anonums")
                .images(imageSet)
                .categories(categorySet)
                .build();
        productRepository.save(product);
        return product;
    }

    @Override
    public List<Product> readByCategory(String category) {
        Category category1 = categoryRepository.findByCategory(category);
        if(category1 == null){
            throw new RuntimeException("Category not found!");
        } else {
            List<Product> productList = productRepository.findByCategoriesContaining(category1).orElseThrow(
                    ()->  new RuntimeException("Product not found!")
            );
            return productList;
        }
    }
    @Override
    public Product readById(long id){
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Product not found!")
        );
        return product;
    }


    @Override
    public List<Product> readByQuery(String query) {
        List<Product> productList = productRepository.findByQuery(query);
        return productList;
    }

    @Override
    public List<Product> readAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> readByAuthor() {
        List<Product> productList = productRepository.findByAuthorname("anonums").orElseThrow(
                ()-> new RuntimeException("Author has not Products!")
        );
        return productList;
    }

    @Override
    public Product update(long id, String name, String description, int quanty, double price, String category, MultipartFile file) {
        Product product = productRepository.findByNameAndAndDescription(name, description).orElseThrow(
                ()-> new RuntimeException("Product not found!")
        );
        String path_image = uploadImageService.saveImage(file);
        Image image = Image.builder()
                .imagePath(path_image)
                .build();
        imageRepository.save(image);
        Set<Image> imageSet = new HashSet<>();
        imageSet.add(image);

        Set<Category> categorySet = new HashSet<>();
        Category category1 = categoryRepository.findByCategory(category);
        if(category1 == null){
            Category newCategory = Category.builder().category(category).build();
            categoryRepository.save(newCategory);
            categorySet.add(newCategory);
        } else {
            categorySet.add(category1);
        }
        product.setName(name);
        product.setDescription(description);
        product.setQuanty(quanty);
        product.setAuthorname("anonums");
        product.setImages(imageSet);
        product.setCategories(categorySet);
        productRepository.save(product);
        return product;
    }

    @Override
    public String deleteById(long id) {
        if(productRepository.existsById(id)){
            return "Product deleted successfully!";
        } else {
            return "Product could not be deleted";
        }
    }

    @Override
    public ProductDto readByIdForService(long id) {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Product not found!")
        );
        ProductDto productDto = ProductDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quanty(product.getQuanty())
                .build();

        return productDto;
    }
}
