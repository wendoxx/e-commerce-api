package org.example.oauth2resourceserverproject.dto.response;

import lombok.Data;
import org.example.oauth2resourceserverproject.model.Order;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long id;
    private List<ProductResponseDTO> products;
    private LocalDate expectedDate;
    private String buyer;

    public OrderResponseDTO(Order order) {
        this.id = order.getId();
        this.products = getProducts();
        this.expectedDate = getExpectedDate();
        this.buyer = getBuyer();
    }
}
