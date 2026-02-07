package com.store.departmentalstore.order;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerEmailIgnoreCase(String customerEmail);

    Optional<Order> findByOrderNumber(String orderNumber);
}
