package org.example.oauth2resourceserverproject.service;

import jakarta.transaction.Transactional;
import org.example.oauth2resourceserverproject.dto.request.OrderRequestDTO;
import org.example.oauth2resourceserverproject.dto.response.OrderResponseDTO;
import org.example.oauth2resourceserverproject.model.Order;
import org.example.oauth2resourceserverproject.reporitory.OrderRepository;
import org.example.oauth2resourceserverproject.reporitory.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    public OrderResponseDTO findById(Long id) {
        return orderRepository.findById(id).map(OrderResponseDTO::new).orElseThrow(() -> new RuntimeException("Order not found."));
    }

    public List<OrderResponseDTO> findAllOrders() {
        return orderRepository.findAll().stream().map(OrderResponseDTO::new).toList();
    }

    @Transactional
    public Order saveAndUpdateOrder(OrderRequestDTO orderRequestDTO) {
        Order order;

        if(orderRequestDTO.getId() != null && orderRepository.existsById(orderRequestDTO.getId())){
            order = orderRepository.findById(orderRequestDTO.getId()).get();
        } else {
            order = new Order();
        }

        order.setBuyer(orderRequestDTO.getBuyer());
        order.setExpectedDate(orderRequestDTO.getExpectedDate());
        order.setProduct(orderRequestDTO.getProduct().stream().map(product -> productRepository.findById(product).orElseThrow(() -> new RuntimeException("Product not found."))).collect(Collectors.toSet()));
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}