package org.example.ecommerce.dto.response;

import lombok.Data;
import org.example.ecommerce.model.Product;
import org.example.ecommerce.utils.AvailabilityStatus;

import java.util.UUID;

@Data
public class ProductResponseDTO {
    private UUID id;
    private String name;
    private String soldBy;
    private double price;
    private String description;
    private AvailabilityStatus availabilityStatus;
    private int stock;

    public ProductResponseDTO(Product product){
       this.id = product.getId();
       this.name = product.getName();
       this.soldBy = product.getSoldBy();
       this.price = product.getPrice();
       this.description = product.getDescription();
       this.availabilityStatus = product.getAvailabilityStatus();
       this.stock = product.getStock();
    }
}