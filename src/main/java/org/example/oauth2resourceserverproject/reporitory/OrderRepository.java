package org.example.oauth2resourceserverproject.reporitory;

import org.example.oauth2resourceserverproject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
