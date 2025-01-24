package org.example.ecommerce.service;

import org.example.ecommerce.dto.response.OrderResponseDTO;
import org.example.ecommerce.infra.config.exception.OrderNotFoundException;
import org.example.ecommerce.model.Order;
import org.example.ecommerce.model.Product;
import org.example.ecommerce.reporitory.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    UUID orderId = UUID.randomUUID();
    UUID order2Id = UUID.randomUUID();
    UUID productId = UUID.randomUUID();
    UUID product2Id = UUID.randomUUID();

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    @BeforeEach
    public void setUp(){
        Product product1 = new Product(productId, "product1", "store1", 220.00, "description1", Set.of());
        Product product2 = new Product(product2Id, "product2", "store2", 220.00, "description2", Set.of());
        Order order1 = new Order(orderId, Set.of(product1), LocalDate.of(2025, 11, 15), "John Doe", 220.00);
        Order order2 = new Order(order2Id, Set.of(product2), LocalDate.of(2025, 11, 11), "John Doe2", 220.00);

        List<Order> orders = List.of(
          order1, order2
        );

        lenient().when(orderRepository.findById(orderId)).thenReturn(Optional.of(order1));
        lenient().when(orderRepository.findAll()).thenReturn(orders);
    }


    @Test
    @DisplayName("This test should return a order by id")
    void shouldReturnAOrderById() {

        OrderResponseDTO result = orderService.findById(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.getId());
        assertEquals("John Doe", result.getBuyer());
        assertEquals(220.00, result.getTotal());
        assertEquals(LocalDate.of(2025,11,15), result.getExpectedDate());
    }

    @Test
    @DisplayName("This test should throws an exception when the order cannot be found.")
    void shouldThrowsAnExceptionWhenTheOrderCannotBeFound() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () ->
            orderService.findById(orderId));
   }

    @Test
    @DisplayName("This test should return a list with all orders")
    void shouldReturnAListWithAllOrders(){

        List<OrderResponseDTO> orders = orderService.findAllOrders();

        assertEquals(orders, orderService.findAllOrders());
    }

}