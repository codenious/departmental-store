package com.store.departmentalstore.service;


import com.store.departmentalstore.entity.Order;
import com.store.departmentalstore.entity.ProductInventory;
import com.store.departmentalstore.enums.OrderStatus;
import com.store.departmentalstore.repository.OrderRepository;
import com.store.departmentalstore.repository.ProductInventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductInventoryRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldCreateBackorderWhenInventoryIsZero() {

        ProductInventory product = new ProductInventory();
        product.setProductId(1L);
        product.setCount(0);
        product.setAvailability(true);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        Order savedOrder = new Order();
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order order = orderService.placeOrder(1L, 1L, 5);

        assertEquals(0, order.getFulfilledQuantity());
        assertEquals(OrderStatus.BACKORDERED, order.getStatus());
    }

    @Test
    void shouldPartiallyFulfillWhenStockIsLessThanOrderQuantity() {

        ProductInventory product = new ProductInventory();
        product.setProductId(1L);
        product.setCount(3);
        product.setAvailability(true);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order order = orderService.placeOrder(1L, 1L, 10);

        assertEquals(3, order.getFulfilledQuantity());
        assertEquals(OrderStatus.PARTIALLY_FULFILLED, order.getStatus());
        assertEquals(0, product.getCount());
    }

    @Test
    void shouldFulfillBackordersInFifoOrder() {

        ProductInventory product = new ProductInventory();
        product.setProductId(1L);
        product.setCount(0);
        product.setAvailability(true);

        Order older = new Order();
        older.setOrderId(1L);
        older.setProductId(1L);
        older.setQuantity(5);
        older.setFulfilledQuantity(0);
        older.setStatus(OrderStatus.BACKORDERED);
        older.setOrderTimestamp(LocalDateTime.now().minusHours(2));

        Order newer = new Order();
        newer.setOrderId(2L);
        newer.setProductId(1L);
        newer.setQuantity(5);
        newer.setFulfilledQuantity(0);
        newer.setStatus(OrderStatus.BACKORDERED);
        newer.setOrderTimestamp(LocalDateTime.now());

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(orderRepository.findByProductIdAndStatusOrderByOrderTimestampAsc(
                1L, OrderStatus.BACKORDERED))
                .thenReturn(List.of(older, newer));

        orderService.replenishInventory(1L, 6);

        assertEquals(5, older.getFulfilledQuantity());
        assertEquals(OrderStatus.COMPLETED, older.getStatus());

        assertEquals(1, newer.getFulfilledQuantity());
        assertEquals(OrderStatus.PARTIALLY_FULFILLED, newer.getStatus());
    }

    @Test
    void shouldThrowExceptionForInvalidQuantity() {

        assertThrows(IllegalArgumentException.class, () ->
                orderService.placeOrder(1L, 1L, 0)
        );
    }




}

