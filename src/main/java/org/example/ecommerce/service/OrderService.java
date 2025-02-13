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

    private static final Logger LOGGER = LogManager.getLogger();

    // Método para encontrar um pedido por ID
    public OrderResponseDTO findById(UUID id) {
        LOGGER.info("Getting order...");
        return orderRepository.findById(id).map(OrderResponseDTO::new).orElseThrow(() -> {
            LOGGER.error("Order not found");
            return new OrderNotFoundException("Order not found.");
        });
    }

    // Método para encontrar todos os pedidos
    public List<OrderResponseDTO> findAllOrders() {
        LOGGER.info("Getting all orders...");

        if (orderRepository.findAll().isEmpty()) {
            LOGGER.error("Orders are empty.");
            throw new OrderListIsEmptyException("Orders are empty!");
        }

        return orderRepository.findAll().stream().map(OrderResponseDTO::new).toList();
    }

    // Método para calcular o total de um pedido
    private Double calculateTotal(OrderRequestDTO orderRequestDTO) {
        double total = 0;
        for (UUID productId : orderRequestDTO.getProducts()) {
            total += productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found.")).getPrice();
        }
        return total;
    }

    // Método para salvar ou atualizar um pedido
    @Transactional
    public Order saveAndUpdateOrder(OrderRequestDTO orderRequestDTO) {
        Order order;

        // Verifica se o ID do pedido existe e se o pedido já está no repositório
        // Se o pedido existir, recupera-o do repositório
        // Se o pedido não existir, cria um novo pedido
        if (orderRequestDTO.getId() != null && orderRepository.existsById(orderRequestDTO.getId())) {
            order = orderRepository.findById(orderRequestDTO.getId()).get();
        } else {
            order = new Order();
        }

        // Converte os IDs dos produtos em objetos Product e os adiciona ao pedido
        Set<Product> products = orderRequestDTO.getProducts()
                .stream()
                .map(productId -> productRepository
                        .findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found.")))
                .collect(Collectors.toSet());

        // Define o status de pagamento do pedido com base no valor do DTO
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

        // Define os valores do pedido com base no DTO
        order.setTotal(calculateTotal(orderRequestDTO));
        order.setBuyer(orderRequestDTO.getBuyer());
        order.setExpectedDate(orderRequestDTO.getExpectedDate());
        order.setProducts(products);
        LOGGER.info("Saving an order...");
        return orderRepository.save(order);
    }

    // Método para deletar um pedido
    public void deleteOrder(UUID id) {
        LOGGER.info("Deleting order...");

        // Verifica se o pedido existe no repositório
        if (orderRepository.findById(id).isEmpty()) {
            LOGGER.error("Order not found.");
            throw new OrderNotFoundException("Order not found");
        }

        // Deleta o pedido do repositório
        orderRepository.deleteById(id);
        LOGGER.info("Order deleted successfully.");
    }
}