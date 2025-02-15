package org.example.ecommerce.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ecommerce.dto.request.ProductRequestDTO;
import org.example.ecommerce.dto.response.ProductResponseDTO;
import org.example.ecommerce.infra.config.exception.ProductListIsEmptyException;
import org.example.ecommerce.infra.config.exception.ProductNotFoundException;
import org.example.ecommerce.model.Product;
import org.example.ecommerce.reporitory.ProductRepository;
import org.example.ecommerce.utils.AvailabilityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    private static final Logger LOGGER = LogManager.getLogger();

    // Method to find a product by ID
    public ProductResponseDTO findById(UUID id) {
        LOGGER.info("Getting product...");
        return productRepository.findById(id)
                .map(ProductResponseDTO::new)
                .orElseThrow(() -> {
                    LOGGER.error("Product not found.");
                    return new ProductNotFoundException("Product not found.");
                });
    }

    // Method to find a product by name
    // if the product is not found, it will throw an exception
    public ProductResponseDTO findByName(String name) {
        return productRepository.findByName(name)
                .map(ProductResponseDTO::new)
                .orElseThrow(() -> {
                    LOGGER.error("Product not found.");
                    return new ProductNotFoundException("Product not found.");
                });
    }

    // Method to find products by store
    // if the list is empty, it will throw an exception
    public List<ProductResponseDTO> findByStore(String soldBy) {
        LOGGER.info("Getting products by store...");

        if(productRepository.findBySoldBy(soldBy).isEmpty()) {
            LOGGER.error("The products List is empty");
            throw new ProductListIsEmptyException("The products list is empty");
        }

        return productRepository.findBySoldBy(soldBy).stream().map(ProductResponseDTO::new).toList();
    }

    // Method to find all products
    // if the list is empty, it will throw an exception
    public List<ProductResponseDTO> findAllProducts() {
        LOGGER.info("Getting all products...");

        if(productRepository.findAll().isEmpty()) {
            LOGGER.error("The products List is empty");
            throw new ProductListIsEmptyException("The products list is empty");
        }

        return productRepository.findAll().stream().map(ProductResponseDTO::new).toList();
    }

    // Method to save or update a product
    // if the product already exists, it will update it. Otherwise, it will create a new one
    public Product saveAndUpdateProduct(ProductRequestDTO productRequestDTO) {
        Product product;
        if(productRequestDTO.getId() != null && productRepository.existsById(productRequestDTO.getId())) {
            product = productRepository.findById(productRequestDTO.getId()).get();
        } else {
            product = new Product();
        }
        
        product.setName(productRequestDTO.getName());
        product.setPrice(productRequestDTO.getPrice());
        product.setSoldBy(productRequestDTO.getSoldBy());
        product.setDescription(productRequestDTO.getDescription());
        product.setStock(productRequestDTO.getStock());
        updateAvailabilityStatus(product);

        return productRepository.save(product);
    }

    // This method updates the availability status of a product
    private void updateAvailabilityStatus(Product product) {
        if (product.getStock() == 0) {
            product.setAvailabilityStatus(AvailabilityStatus.OUT_OF_STOCK);
        } else {
            product.setAvailabilityStatus(AvailabilityStatus.IN_STOCK);
        }
    }

    // This method updates the stock of the products
    // this method calls the updateAvailabilityStatus method to update the availability status of the product
    public void updateProductStock(Set<Product> products) {
        for (Product product : products) {
            if (product.getStock() > 0) {
                product.setStock(product.getStock() - 1);
                updateAvailabilityStatus(product);
                productRepository.save(product);
            }
        }
    }

    // Method to delete a product
    // if the product is not found, it will throw an exception
    public void deleteProduct(UUID id) {
        LOGGER.info("Deleting product...");

        if (productRepository.findById(id).isEmpty()) {
            LOGGER.error("Product not found.");
            throw new ProductNotFoundException("Product not found.");
        }

        productRepository.deleteById(id);
        LOGGER.info("Product deleted successfully.");
    }
}