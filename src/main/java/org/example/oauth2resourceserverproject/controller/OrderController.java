package org.example.oauth2resourceserverproject.controller;

import org.example.oauth2resourceserverproject.dto.request.OrderRequestDTO;
import org.example.oauth2resourceserverproject.dto.response.OrderResponseDTO;
import org.example.oauth2resourceserverproject.model.Order;
import org.example.oauth2resourceserverproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/public/get-order/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping("/private/all-orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @PostMapping("/public/create-order")
    public ResponseEntity<Order> saveOrder(OrderRequestDTO orderRequestDTO) {
        return ResponseEntity.status(201).body(orderService.saveAndUpdateOrder(orderRequestDTO));
    }

    @PutMapping("/public/update-order")
    public ResponseEntity<Order> updateOrder(OrderRequestDTO orderRequestDTO) {
        return ResponseEntity.status(201).body(orderService.saveAndUpdateOrder(orderRequestDTO));
    }

    @DeleteMapping("/public/delete-order/{id}")
    public ResponseEntity<OrderResponseDTO> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
