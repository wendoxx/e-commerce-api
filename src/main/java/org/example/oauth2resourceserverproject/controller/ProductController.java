package org.example.oauth2resourceserverproject.controller;

import org.example.oauth2resourceserverproject.dto.request.ProductRequestDTO;
import org.example.oauth2resourceserverproject.dto.response.ProductResponseDTO;
import org.example.oauth2resourceserverproject.model.Product;
import org.example.oauth2resourceserverproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/public/get-product-id/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
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
    public ResponseEntity<Product> saveProduct(ProductRequestDTO productRequestDTO) {
        return ResponseEntity.status(201).body(productService.saveAndUpdateProduct(productRequestDTO));
    }

    @PutMapping("/private/update-product")
    public ResponseEntity<Product> updateProduct(ProductRequestDTO productRequestDTO) {
        return ResponseEntity.status(201).body(productService.saveAndUpdateProduct(productRequestDTO));
    }

    @DeleteMapping("/private/delete-product{id}")
    public ResponseEntity<ProductResponseDTO> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
