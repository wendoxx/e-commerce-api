package org.example.ecommerce.service;

import org.example.ecommerce.dto.request.ProductRequestDTO;
import org.example.ecommerce.dto.response.ProductResponseDTO;
import org.example.ecommerce.model.Product;
import org.example.ecommerce.reporitory.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductResponseDTO findById(UUID id) {
        return productRepository.findById(id)
                .map(ProductResponseDTO::new)
                .orElseThrow(() -> new RuntimeException("Id not found."));
    }

    public ProductResponseDTO findByName(String name) {
        return productRepository.findByName(name)
                .map(ProductResponseDTO::new)
                .orElseThrow(() -> new RuntimeException("Product not found."));
    }

    public List<ProductResponseDTO> findByStore(String soldBy) {
        return productRepository.findBySoldBy(soldBy).stream().map(ProductResponseDTO::new).toList();
    }

    public List<ProductResponseDTO> findAllProducts() {
        return productRepository.findAll().stream().map(ProductResponseDTO::new).toList();
    }

    public Product saveAndUpdateProduct(ProductRequestDTO productRequestDTO) {
        Product product;
        if(productRequestDTO.getId() != null && productRepository.existsById(productRequestDTO.getId())) {
            product = productRepository.findById(productRequestDTO.getId()).get();
        } else {
            product = new Product();
        }

        product.setId(productRequestDTO.getId());
        product.setName(productRequestDTO.getName());
        product.setPrice(productRequestDTO.getPrice());
        product.setSoldBy(productRequestDTO.getSoldBy());
        product.setDescription(productRequestDTO.getDescription());

        return productRepository.save(product);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}