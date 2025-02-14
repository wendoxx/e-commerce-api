package org.example.ecommerce.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ecommerce.utils.PaymentStatus;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderRequestDTO {
    private UUID id;
    private Set<UUID> products;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expectedDate;
    private String buyer;
    private Double total;
    private String paymentStatus;
}