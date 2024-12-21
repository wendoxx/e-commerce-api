package org.example.oauth2resourceserverproject.dto.response;

import lombok.Data;
import org.example.oauth2resourceserverproject.model.Product;

@Data
public class Long {
    private java.lang.Long id;
    private String name;
    private String soldBy;
    private double price;
    private String description;

    public Long(Product product){
       this.id = product.getId();
       this.name = product.getName();
       this.soldBy = product.getSoldBy();
       this.price = product.getPrice();
       this.description = product.getDescription();
    }
}