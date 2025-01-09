package org.example.ecommerce.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
public class OrderRequestDTO {
    private UUID id;
    private Set<UUID> products;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expectedDate;
    private String buyer;
    private Double total;
}
