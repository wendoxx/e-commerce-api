package org.example.ecommerce.service;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ecommerce.dto.request.OrderRequestDTO;
import org.example.ecommerce.dto.response.OrderResponseDTO;
import org.example.ecommerce.infra.config.exception.OrderListIsEmptyException;
import org.example.ecommerce.infra.config.exception.OrderNotFoundException;
import org.example.ecommerce.model.Order;
import org.example.ecommerce.model.Product;
import org.example.ecommerce.reporitory.OrderRepository;
import org.example.ecommerce.reporitory.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    private static final Logger LOGGER = LogManager.getLogger();

    public OrderResponseDTO findById(UUID id) {
        LOGGER.info("Getting order...");
        return orderRepository.findById(id).map(OrderResponseDTO::new).orElseThrow(() -> {
            LOGGER.error("Order not found");
            return new OrderNotFoundException("Order not found.");
        });
    }

    public List<OrderResponseDTO> findAllOrders() {
        LOGGER.info("Getting all orders...");

        if(orderRepository.findAll().isEmpty()) {
            LOGGER.error("Orders are empty.");
            throw new OrderListIsEmptyException("Orders are empty!");
        }

        return orderRepository.findAll().stream().map(OrderResponseDTO::new).toList();
    }

    private Double calculateTotal(OrderRequestDTO orderRequestDTO) {
        double total = 0;
        for (UUID productId : orderRequestDTO.getProducts()) {
            total += productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found.")).getPrice();
        }
        return total;
    }

    @Transactional
    public Order saveAndUpdateOrder(OrderRequestDTO orderRequestDTO) {
        Order order;

        if(orderRequestDTO.getId() != null && orderRepository.existsById(orderRequestDTO.getId())){
            order = orderRepository.findById(orderRequestDTO.getId()).get();
        } else {
            order = new Order();
        }

        Set<Product> products = orderRequestDTO.getProducts()
                .stream()
                .map(productId -> productRepository
                        .findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found.")))
                .collect(Collectors.toSet());

        order.setTotal(calculateTotal(orderRequestDTO));
        order.setBuyer(orderRequestDTO.getBuyer());
        order.setExpectedDate(orderRequestDTO.getExpectedDate());
        order.setProducts(products);
        LOGGER.info("Saving an order...");
        return orderRepository.save(order);
    }

    public void deleteOrder(UUID id) {
        LOGGER.info("Deleting order...");

        if(orderRepository.findById(id).isEmpty()) {
            LOGGER.error("Order not found.");
            throw new OrderNotFoundException("Order not found");
        }

        orderRepository.deleteById(id);
        LOGGER.info("Order deleted successfully.");
    }
}