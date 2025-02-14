package org.example.ecommerce.service;

import org.example.ecommerce.dto.request.OrderRequestDTO;
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

    OrderRequestDTO OrderDTO;
    Order savedOrder;
    Order order1;
    Order order2;
    List<Order> orders;

    @BeforeEach
    public void setUp(){

        OrderDTO = new OrderRequestDTO(orderId, Set.of(), LocalDate.of(2025, 11, 15), "John Doe", 2.0, null);

        savedOrder = new Order();
        savedOrder.setId(orderId);
        savedOrder.setProducts(Set.of());
        savedOrder.setExpectedDate(LocalDate.of(2025,11,15));
        savedOrder.setBuyer("buyer");
        savedOrder.setTotal(2.0);

        Product product1 = new Product(productId, "product1", "store1", 220.00, "description1", Set.of());
        Product product2 = new Product(product2Id, "product2", "store2", 220.00, "description2", Set.of());
        order1 = new Order(orderId, Set.of(product1), LocalDate.of(2025, 11, 15), "John Doe", 220.00, null);
        order2 = new Order(order2Id, Set.of(product2), LocalDate.of(2025, 11, 11), "John Doe2", 220.00, null);

        orders = List.of(
          order1, order2
        );

        lenient().when(orderRepository.findById(orderId)).thenReturn(Optional.of(order1));
        lenient().when(orderRepository.findAll()).thenReturn(orders);
        lenient().when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
    }

    @Test
    @DisplayName("This test should return the correct order details when searched by ID")
    void shouldReturnCorrectOrderDetailsById() {

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
    void shouldReturnAListWithAllOrders() {

        List<OrderResponseDTO> result = orderService.findAllOrders();

        assertEquals(orders.size(), result.size());
        assertEquals(order1.getId(), result.get(0).getId());
        assertEquals(order2.getId(), result.get(1).getId());
    }

    
    @Test
    @DisplayName("This test should save a new order")
    void shouldSaveANewOrder() {
        Order result = orderService.saveAndUpdateOrder(OrderDTO);

        assertEquals(result.getId(), savedOrder.getId());
        assertEquals(result.getProducts(), savedOrder.getProducts());
        assertEquals(result.getExpectedDate(), savedOrder.getExpectedDate());
        assertEquals(result.getBuyer(), savedOrder.getBuyer());
        assertEquals(result.getTotal(), savedOrder.getTotal());
    }

    @Test
    @DisplayName("This test should delete a order")
    void shouldDeleteAOrder() {
        lenient().when(orderRepository.existsById(orderId)).thenReturn(true);

        orderService.deleteOrder(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
    }
}