
package com.store.departmentalstore.repository;

import com.store.departmentalstore.entity.Order;
import com.store.departmentalstore.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByProductIdAndStatusOrderByOrderTimestampAsc(
            Long productId,
            OrderStatus status
    );
}

