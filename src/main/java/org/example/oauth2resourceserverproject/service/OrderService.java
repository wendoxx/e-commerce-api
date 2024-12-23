package org.example.oauth2resourceserverproject.service;

import jakarta.transaction.Transactional;
import org.example.oauth2resourceserverproject.dto.request.OrderRequestDTO;
import org.example.oauth2resourceserverproject.dto.response.OrderResponseDTO;
import org.example.oauth2resourceserverproject.model.Order;
import org.example.oauth2resourceserverproject.model.Product;
import org.example.oauth2resourceserverproject.reporitory.OrderRepository;
import org.example.oauth2resourceserverproject.reporitory.ProductRepository;
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

    public OrderResponseDTO findById(UUID id) {
        return orderRepository.findById(id).map(OrderResponseDTO::new).orElseThrow(() -> new RuntimeException("Order not found."));
    }

    public List<OrderResponseDTO> findAllOrders() {
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
        return orderRepository.save(order);
    }

    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }
}