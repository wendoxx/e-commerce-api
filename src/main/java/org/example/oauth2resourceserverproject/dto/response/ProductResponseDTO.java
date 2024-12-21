package org.example.oauth2resourceserverproject.dto.response;

import lombok.Data;
import org.example.oauth2resourceserverproject.model.Product;

import java.util.UUID;

@Data
public class ProductResponseDTO {
    private UUID id;
    private String name;
    private String soldBy;
    private double price;
    private String description;

    public ProductResponseDTO(Product product){
       this.id = product.getId();
       this.name = product.getName();
       this.soldBy = product.getSoldBy();
       this.price = product.getPrice();
       this.description = product.getDescription();
    }
}