package org.example.ecommerce.service;

import jakarta.transaction.Transactional;
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
import org.example.ecommerce.utils.PaymentStatus;
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

    @Autowired
    private ProductService productService;

    private static final Logger LOGGER = LogManager.getLogger();

    // Method to find an order by ID
    public OrderResponseDTO findById(UUID id) {
        LOGGER.info("Getting order...");
        return orderRepository.findById(id).map(OrderResponseDTO::new).orElseThrow(() -> {
            LOGGER.error("Order not found");
            return new OrderNotFoundException("Order not found.");
        });
    }

    // Method to find all orders
    public List<OrderResponseDTO> findAllOrders() {
        LOGGER.info("Getting all orders...");

        if (orderRepository.findAll().isEmpty()) {
            LOGGER.error("Orders are empty.");
            throw new OrderListIsEmptyException("Orders are empty!");
        }

        return orderRepository.findAll().stream().map(OrderResponseDTO::new).toList();
    }

    // Method to calculate the total value of the order
    private Double calculateTotal(OrderRequestDTO orderRequestDTO) {
        double total = 0;
        for (UUID productId : orderRequestDTO.getProducts()) {
            total += productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found.")).getPrice();
        }
        return total;
    }

    // Method to save and update an order
    @Transactional
    public Order saveAndUpdateOrder(OrderRequestDTO orderRequestDTO) {
        Order order;

        // Check if the order ID exists and if the order is already in the repository
        // If the order exists, retrieve it from the repository
        // If the order does not exist, create a new order
        if (orderRequestDTO.getId() != null && orderRepository.existsById(orderRequestDTO.getId())) {
            order = orderRepository.findById(orderRequestDTO.getId()).get();
        } else {
            order = new Order();
        }

        // Convert the product IDs to Product objects and add them to the order
        Set<Product> products = orderRequestDTO.getProducts()
                .stream()
                .map(productId -> productRepository
                        .findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found.")))
                .collect(Collectors.toSet());

        productService.updateProductStock(products);

        // Set the payment status of the order based on the value from the DTO
        if (orderRequestDTO.getPaymentStatus().isEmpty()) {
            order.setPaymentStatus(PaymentStatus.PENDING);
        } else if (orderRequestDTO.getPaymentStatus().equals("PAID")) {
            order.setPaymentStatus(PaymentStatus.PAID);
        } else if (orderRequestDTO.getPaymentStatus().equals("SHIPPED")) {
            order.setPaymentStatus(PaymentStatus.SHIPPED);
        } else if (orderRequestDTO.getPaymentStatus().equals("DELIVERED")) {
            order.setPaymentStatus(PaymentStatus.DELIVERED);
        } else if (orderRequestDTO.getPaymentStatus().equals("CANCELED")) {
            order.setPaymentStatus(PaymentStatus.CANCELED);
        }

        // Set the order values
        order.setTotal(calculateTotal(orderRequestDTO));
        order.setBuyer(orderRequestDTO.getBuyer());
        order.setExpectedDate(orderRequestDTO.getExpectedDate());
        order.setProducts(products);
        LOGGER.info("Saving an order...");
        return orderRepository.save(order);
    }

    // Method to delete an order
    public void deleteOrder(UUID id) {
        LOGGER.info("Deleting order...");

        // Verify if the order exists
        if (orderRepository.findById(id).isEmpty()) {
            LOGGER.error("Order not found.");
            throw new OrderNotFoundException("Order not found");
        }

        // Delet the order in the repository
        orderRepository.deleteById(id);
        LOGGER.info("Order deleted successfully.");
    }
}