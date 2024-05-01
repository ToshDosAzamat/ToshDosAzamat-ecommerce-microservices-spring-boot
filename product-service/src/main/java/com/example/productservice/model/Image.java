package com.example.productservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "IMAGES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String imagePath;
}
