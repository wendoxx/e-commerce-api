package org.example.ecommerce.controller;

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
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/public/get-product-id/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/public/product-name")
    public ResponseEntity<ProductResponseDTO> getProductByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.findByName(name));
    }

    @GetMapping("/public/product-by-store")
    public ResponseEntity<List<ProductResponseDTO>> getProductByStore(String soldBy) {
        return ResponseEntity.ok(productService.findByStore(soldBy));

    }

    @GetMapping("/public/all-products")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @PostMapping("/private/save-product")
    public ResponseEntity<Product> saveProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity.status(201).body(productService.saveAndUpdateProduct(productRequestDTO));
    }

    @PutMapping("/private/update-product")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity.status(201).body(productService.saveAndUpdateProduct(productRequestDTO));
    }

    @DeleteMapping("/private/delete-product{id}")
    public ResponseEntity<ProductResponseDTO> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
