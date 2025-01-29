package org.example.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class ProductRequestDTO {
    private UUID id;
    private String name;
    private String soldBy;
    private double price;
    private String description;
}