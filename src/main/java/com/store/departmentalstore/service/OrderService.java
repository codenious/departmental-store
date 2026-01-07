
package com.store.departmentalstore.service;

import com.store.departmentalstore.entity.*;
import com.store.departmentalstore.enums.OrderStatus;
import com.store.departmentalstore.exceptions.ResourceNotFoundException;
import com.store.departmentalstore.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductInventoryRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductInventoryRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order placeOrder(Long productId, Long customerId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        ProductInventory product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Order order = new Order();
        order.setProductId(productId);
        order.setCustomerId(customerId);
        order.setQuantity(quantity);
        order.setOrderTimestamp(LocalDateTime.now());

        if (!product.isAvailability() || product.getCount() == 0) {
            // Full backorder
            order.setFulfilledQuantity(0);
            order.setStatus(OrderStatus.BACKORDERED);
        } else if (product.getCount() >= quantity) {
            // fulfillment of order
            product.setCount(product.getCount() - quantity);
            productRepository.save(product);

            order.setFulfilledQuantity(quantity);
            order.setStatus(OrderStatus.COMPLETED);
        } else {
            // Partial fulfillment
            int fulfilled = product.getCount();
            product.setCount(0);
            productRepository.save(product);

            order.setFulfilledQuantity(fulfilled);
            order.setStatus(OrderStatus.PARTIALLY_FULFILLED);
        }

        return orderRepository.save(order);
    }

    @Transactional
    public void replenishInventory(Long productId, int addedStock) {

        if (addedStock <= 0) {
            throw new IllegalArgumentException("Added stock must be more than 0");
        }

        ProductInventory product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setCount(product.getCount() + addedStock);
        productRepository.save(product);

        List<Order> backorders =
                orderRepository.findByProductIdAndStatusOrderByOrderTimestampAsc(
                        productId,
                        OrderStatus.BACKORDERED
                );

        for (Order order : backorders) {
            if (product.getCount() == 0) {
                break;
            }

            int remaining = order.getQuantity() - order.getFulfilledQuantity();
            int fulfillNow = Math.min(remaining, product.getCount());

            order.setFulfilledQuantity(order.getFulfilledQuantity() + fulfillNow);
            product.setCount(product.getCount() - fulfillNow);

            if (order.getFulfilledQuantity() == order.getQuantity()) {
                order.setStatus(OrderStatus.COMPLETED);
            } else {
                order.setStatus(OrderStatus.PARTIALLY_FULFILLED);
            }

            orderRepository.save(order);
        }

        productRepository.save(product);
    }

}

