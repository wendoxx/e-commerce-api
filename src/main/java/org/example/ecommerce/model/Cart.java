package org.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "tb_cart")
public class Cart {

    @Id
    private UUID id;
    @Column
    @ManyToMany
    private Set<Product> products = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
