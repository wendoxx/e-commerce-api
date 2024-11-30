package org.example.oauth2resourceserverproject.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sold-by")
    private String soldBy;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;
}
