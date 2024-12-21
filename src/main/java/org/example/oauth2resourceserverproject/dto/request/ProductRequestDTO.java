package org.example.oauth2resourceserverproject.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductRequestDTO {
    private UUID id;
    private String name;
    private String soldBy;
    private double price;
    private String description;
}