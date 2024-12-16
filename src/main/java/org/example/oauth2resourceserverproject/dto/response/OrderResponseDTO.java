package org.example.oauth2resourceserverproject.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.example.oauth2resourceserverproject.model.Order;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class OrderResponseDTO {
    private Long id;
    private Set<ProductResponseDTO> product;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expectedDate;
    private String buyer;

    public OrderResponseDTO(Order order) {
        this.id = order.getId();
        this.product = order.getProduct() != null
                ? order.getProduct().stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toSet())
                : null;
        this.expectedDate = order.getExpectedDate();
        this.buyer = order.getBuyer();
    }
}
