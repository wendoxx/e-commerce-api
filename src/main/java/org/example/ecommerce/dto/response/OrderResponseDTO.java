package org.example.ecommerce.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.example.ecommerce.model.Order;
import org.example.ecommerce.model.Product;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
public class OrderResponseDTO {
    private UUID id;
    private Set<Product> products;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expectedDate;
    private String buyer;
    private Double total;

    public OrderResponseDTO(Order order) {
        this.id = order.getId();
        this.products = order.getProducts();
        this.expectedDate = order.getExpectedDate();
        this.buyer = order.getBuyer();
        this.total = order.getTotal();
    }
}
