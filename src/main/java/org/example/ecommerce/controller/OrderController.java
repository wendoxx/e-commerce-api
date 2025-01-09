package org.example.ecommerce.controller;

import org.example.ecommerce.dto.request.OrderRequestDTO;
import org.example.ecommerce.dto.response.OrderResponseDTO;
import org.example.ecommerce.model.Order;
import org.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/public/get-order-id/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping("/public/all-orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @PostMapping("/public/save-order")
    public ResponseEntity<Order> saveOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity.status(201).body(orderService.saveAndUpdateOrder(orderRequestDTO));
    }

    @PutMapping("/public/update-order")
    public ResponseEntity<Order> updateOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity.status(201).body(orderService.saveAndUpdateOrder(orderRequestDTO));
    }

    @DeleteMapping("/public/delete-order/{id}")
    public ResponseEntity<OrderResponseDTO> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
