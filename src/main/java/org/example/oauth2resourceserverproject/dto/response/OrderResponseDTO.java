package org.example.oauth2resourceserverproject.dto.response;

import lombok.Data;
import org.example.oauth2resourceserverproject.model.Order;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long id;
    private List<ProductResponseDTO> product;
    private LocalDate expectedDate;
    private String buyer;

    public OrderResponseDTO(Order order) {
        this.id = order.getId();
        this.product = getProduct();
        this.expectedDate = getExpectedDate();
        this.buyer = getBuyer();
    }
}
