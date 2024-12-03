package org.example.oauth2resourceserverproject.service;

import org.example.oauth2resourceserverproject.dto.request.OrderRequestDTO;
import org.example.oauth2resourceserverproject.dto.response.OrderResponseDTO;
import org.example.oauth2resourceserverproject.model.Order;
import org.example.oauth2resourceserverproject.reporitory.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public OrderResponseDTO findById(Long id) {
        return orderRepository.findById(id).map(OrderResponseDTO::new).orElseThrow(() -> new RuntimeException("Order not found."));
    }

    public Order saveAndUpdateOrder(OrderRequestDTO orderRequestDTO) {
        Order order;

        if(orderRequestDTO.getId() != null && orderRepository.existsById(orderRequestDTO.getId())){
            order = orderRepository.findById(orderRequestDTO.getId()).get();
        } else {
            order = new Order();
        }
        order.setId(orderRequestDTO.getId());
        order.setBuyer(orderRequestDTO.getBuyer());
        order.setSeller(orderRequestDTO.getSeller());
        order.setExpectedDate(orderRequestDTO.getExpectedDate());
        order.setProducts(orderRequestDTO.getProducts());

        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}