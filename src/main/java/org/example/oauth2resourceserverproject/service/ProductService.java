package org.example.oauth2resourceserverproject.service;

import org.example.oauth2resourceserverproject.dto.request.ProductRequestDTO;
import org.example.oauth2resourceserverproject.dto.response.ProductResponseDTO;
import org.example.oauth2resourceserverproject.model.Product;
import org.example.oauth2resourceserverproject.reporitory.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductResponseDTO findById(Long id) {
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

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}