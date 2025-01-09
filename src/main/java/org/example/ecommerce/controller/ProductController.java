package org.example.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.ecommerce.dto.request.ProductRequestDTO;
import org.example.ecommerce.dto.response.ProductResponseDTO;
import org.example.ecommerce.model.Product;
import org.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
@Tag(name = "Product Controller", description = "This controller is responsible for handling order related operations.")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(
            method = "GET",
            summary = "Get a product by id",
            description = "This endpoint retrieves a product by its id"
    )
    @ApiResponse(responseCode = "200", description = "Product retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/public/get-product-id/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @Operation(
            method = "GET",
            summary = "Get a product by name",
            description = "This endpoint retrieves a product by its name"
    )
    @ApiResponse(responseCode = "200", description = "Product retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/public/product-name")
    public ResponseEntity<ProductResponseDTO> getProductByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.findByName(name));
    }

    @Operation(
            method = "GET",
            summary = "List products by store",
            description = "This endpoint retrieves a list of all products by its store"
    )
    @ApiResponse(responseCode = "200", description = "Product retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/public/product-by-store")
    public ResponseEntity<List<ProductResponseDTO>> getProductByStore(String soldBy) {
        return ResponseEntity.ok(productService.findByStore(soldBy));

    }

    @Operation(
            method = "GET",
            summary = "Get all products",
            description = "This endpoint retrieves a list of all products"
    )
    @ApiResponse(responseCode = "200", description = "Product retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/public/all-products")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @Operation(
            method = "POST",
            summary = "Save a product",
            description = "This endpoint saves a product"
    )
    @ApiResponse(responseCode = "201", description = "Product saved successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/private/save-product")
    public ResponseEntity<Product> saveProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity.status(201).body(productService.saveAndUpdateProduct(productRequestDTO));
    }

    @Operation(
            method = "PUT",
            summary = "Update a product",
            description = "This endpoint updates a product"
    )
    @ApiResponse(responseCode = "201", description = "Product updated successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PutMapping("/private/update-product")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity.status(201).body(productService.saveAndUpdateProduct(productRequestDTO));
    }

    @Operation(
            method = "DELETE",
            summary = "Delete a product by id",
            description = "This endpoint deletes a product by its id"
    )
    @ApiResponse(responseCode = "204", description = "No content")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping("/private/delete-product{id}")
    public ResponseEntity<ProductResponseDTO> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}