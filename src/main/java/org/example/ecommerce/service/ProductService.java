package org.example.ecommerce.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOGGER = LogManager.getLogger();

    public ProductResponseDTO findById(UUID id) {
        LOGGER.info("Getting product...");
        return productRepository.findById(id)
                .map(ProductResponseDTO::new)
                .orElseThrow(() -> {
                    LOGGER.error("Product not found.");
                    return new RuntimeException("Product not found.");
                });
    }

    public ProductResponseDTO findByName(String name) {
        return productRepository.findByName(name)
                .map(ProductResponseDTO::new)
                .orElseThrow(() -> {
                    LOGGER.error("Product not found.");
                    return  new RuntimeException("Product not found.");
                });
    }

    public List<ProductResponseDTO> findByStore(String soldBy) {
        LOGGER.info("Getting products by store...");

        if(productRepository.findBySoldBy(soldBy).isEmpty()) {
            LOGGER.error("The products List is empty");
            throw new RuntimeException("The products list is empty");
        }

        return productRepository.findBySoldBy(soldBy).stream().map(ProductResponseDTO::new).toList();
    }

    public List<ProductResponseDTO> findAllProducts() {
        LOGGER.info("Getting all products...");

        if(productRepository.findAll().isEmpty()) {
            LOGGER.error("The products List is empty");
            throw new RuntimeException("The products list is empty");
        }

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
        LOGGER.info("Deleting product...");

        if (productRepository.findById(id).isEmpty()) {
            LOGGER.error("Product not found.");
            throw new RuntimeException("Product not found.");
        }

        productRepository.deleteById(id);
        LOGGER.info("Product deleted successfully.");
    }
}