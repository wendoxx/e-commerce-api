package org.example.oauth2resourceserverproject.dto.response;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String soldBy;
    private double price;
    private String description;
}