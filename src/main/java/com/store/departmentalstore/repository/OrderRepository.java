
package com.store.departmentalstore.repository;

import com.store.departmentalstore.entity.Order;
import com.store.departmentalstore.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderId(String orderId);
    Page<Order> findByCustomerId(Long customerId, Pageable pageable);

}

