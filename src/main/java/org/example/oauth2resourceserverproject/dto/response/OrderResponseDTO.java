package org.example.oauth2resourceserverproject.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderResponseDTO {
    private Long id;
    private String products;
    private LocalDate expectedDate;
    private String buyer;
    private String seller;
}
