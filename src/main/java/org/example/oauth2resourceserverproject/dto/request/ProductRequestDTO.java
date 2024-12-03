package org.example.oauth2resourceserverproject.dto.request;

import lombok.Data;

@Data
public class ProductRequestDTO  {
    private Long id;
    private String name;
    private String soldBy;
    private double price;
    private String description;
}