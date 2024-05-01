package com.example.productservice.controller;


import com.example.productservice.service.ProductService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createproduct(@RequestParam String name, @RequestParam String description,
                                                 @RequestParam int quanty, @RequestParam double price,
                                                 @RequestParam String category, @RequestParam("image") MultipartFile file){
        return new ResponseEntity<>(productService.create(name, description, quanty, price, category, file), HttpStatus.CREATED);
    }

    @PostMapping("/readbycategory")
    public ResponseEntity<Object> readbycategory(@RequestParam String category){
        return new ResponseEntity<>(productService.readByCategory(category), HttpStatus.OK);
    }
    @PostMapping("/readbyquery")
    public ResponseEntity<Object> readbyquery(@RequestParam String query){
        return new ResponseEntity<>(productService.readByQuery(query), HttpStatus.OK);
    }
    @GetMapping("/readall")
    public ResponseEntity<Object> readalls(){
        return new ResponseEntity<>(productService.readAll(), HttpStatus.OK);
    }
    @GetMapping("/readbyid/{id}")
    public ResponseEntity<Object> readbyidforclient(@PathVariable long id){
        return new ResponseEntity<>(productService.readById(id), HttpStatus.OK);
    }

    @PostMapping("/readbyauthor")
    public ResponseEntity<Object> readbyauthor(){
        return new ResponseEntity<>(productService.readByAuthor(), HttpStatus.OK);
    }
    @PostMapping("/update")
    public ResponseEntity<Object> createproduct(@RequestParam long id, @RequestParam String name, @RequestParam String description,
                                                 @RequestParam int quanty, @RequestParam double price,
                                                 @RequestParam String category, @RequestParam("image") MultipartFile file){
        return new ResponseEntity<>(productService.update(id, name, description, quanty, price, category, file), HttpStatus.CREATED);
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deletebyid(@RequestParam long id){
        return  new ResponseEntity<>(productService.deleteById(id), HttpStatus.OK);
    }
    @GetMapping("/readbyservice/{id}")
    public ResponseEntity<Object> readbyproductservice(@PathVariable long id){
        return new ResponseEntity<>(productService.readByIdForService(id), HttpStatus.OK);
    }

}
