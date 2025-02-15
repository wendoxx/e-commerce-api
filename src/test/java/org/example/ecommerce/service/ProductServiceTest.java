package org.example.ecommerce.service;

import org.example.ecommerce.dto.request.ProductRequestDTO;
import org.example.ecommerce.dto.response.ProductResponseDTO;
import org.example.ecommerce.infra.config.exception.ProductNotFoundException;
import org.example.ecommerce.model.Product;
import org.example.ecommerce.reporitory.ProductRepository;
import org.example.ecommerce.utils.AvailabilityStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    UUID productId = UUID.randomUUID();
    UUID product2Id = UUID.randomUUID();

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    ProductRequestDTO productRequestDTO;
    Product product;
    Product product2;
    List<Product> products;

    @BeforeEach
    public void setUp() {

        productRequestDTO = new ProductRequestDTO(productId, "product1", "store1", 20.00, "description1", 10);

        product = new Product(productId, "product1", "store1", 20.00, "description1",91, AvailabilityStatus.IN_STOCK, Set.of());
        product2 = new Product(product2Id, "product2", "store2", 20.00, "description2", 10, AvailabilityStatus.IN_STOCK, Set.of());

        products = List.of(
                product, product2
        );

        lenient().when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        lenient().when(productRepository.findAll()).thenReturn(products);
        lenient().when(productRepository.findByName(product.getName())).thenReturn(Optional.of(product));
        lenient().when(productRepository.save(any(Product.class))).thenReturn(product);
    }

    @Test
    @DisplayName("This test should return correct product details when search by ID")
    void shouldReturnCorrectProductDetailsById() {
        ProductResponseDTO result = productService.findById(productId);

        assertEquals(result.getId(), product.getId());
        assertEquals(result.getName(), product.getName());
        assertEquals(result.getSoldBy(), product.getSoldBy());
        assertEquals(result.getPrice(), product.getPrice());
        assertEquals(result.getDescription(), product.getDescription());
    }

    @Test
    @DisplayName("This test should throws an exception when the order cannot be found")
    void shouldThrowsAnExceptionWhenTheProductCannotBeFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () ->
                productService.findById(productId));
    }

    @Test
    @DisplayName("This test should return the correct product details when searched by name")
    void shouldReturnCorrectProductDetailsByName() {

        ProductResponseDTO result = productService.findByName(product.getName());

        assertEquals(result.getId(), product.getId());
        assertEquals(result.getName(), product.getName());
        assertEquals(result.getSoldBy(), product.getSoldBy());
        assertEquals(result.getPrice(), product.getPrice());
        assertEquals(result.getDescription(), product.getDescription());
    }

    @Test
    @DisplayName("This test should return a list with all products")
    void shouldReturnAListWithAllProducts() {
        List<ProductResponseDTO> result = productService.findAllProducts();

        assertEquals(products.size(), result.size());
        assertEquals(product.getId(), result.get(0).getId());
        assertEquals(product2.getId(), result.get(1).getId());
    }

    @Test
    @DisplayName("This test should save a new product")
    void shouldSaveAProduct() {
        Product result = productService.saveAndUpdateProduct(productRequestDTO);

        assertEquals(result.getId(), product.getId());
        assertEquals(result.getName(), product.getName());
        assertEquals(result.getPrice(), product.getPrice());
        assertEquals(result.getSoldBy(), product.getSoldBy());
        assertEquals(result.getDescription(), product.getDescription());
    }

    @Test
    @DisplayName("This test should delete a order")
    void shouldDeleteAOrder() {
        lenient().when(productRepository.existsById(productId)).thenReturn(true);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }
}
