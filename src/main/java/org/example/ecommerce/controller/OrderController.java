package org.example.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v2/order")
@Tag(name = "Order Controller", description = "This controller is responsible for handling order related operations.")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(
            method = "GET",
            summary = "Get an order by id",
            description = "This endpoint retrieves an order by its id"
    )
    @ApiResponse(responseCode = "200", description = "Order retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/public/get-order-id/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @Operation(
            method = "GET",
            summary = "Get all orders",
            description = "This endpoint retrieves all orders"
    )
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/public/all-orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @Operation(
            method = "POST",
            summary = "Save order",
            description = "This endpoint saves an order"
    )
    @ApiResponse(responseCode = "201", description = "Order saved successfully")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/public/save-order")
    public ResponseEntity<Order> saveOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity.status(201).body(orderService.saveAndUpdateOrder(orderRequestDTO));
    }

    @Operation(
            method = "PUT",
            summary = "Update order",
            description = "This endpoint updates an order"
    )
    @ApiResponse(responseCode = "201", description = "Order updated successfully")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PutMapping("/public/update-order")
    public ResponseEntity<Order> updateOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity.status(201).body(orderService.saveAndUpdateOrder(orderRequestDTO));
    }

    @Operation(
            method = "DELETE",
            summary = "Delete an order by id",
            description = "This endpoint deletes an order"
    )
    @ApiResponse(responseCode = "204", description = "No content")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping("/public/delete-order/{id}")
    public ResponseEntity<OrderResponseDTO> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}