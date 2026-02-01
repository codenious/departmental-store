
package com.store.departmentalstore.entity;

import com.store.departmentalstore.enums.OrderStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long internalOrderId;

    private String orderId;

    private Long productId;
    private Long customerId;

    private int quantity;          // original requested
    private int fulfilledQuantity; // how much has been fulfilled

    private LocalDateTime orderTimestamp;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // getters and setters


    public String getOrderId() {
        return orderId;
    }


    public void setInternalOrderId(Long internalOrderId) {
        this.internalOrderId = internalOrderId;
    }

    public Long getInternalOrderId() {
        return internalOrderId;
    }


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getFulfilledQuantity() {
        return fulfilledQuantity;
    }

    public void setFulfilledQuantity(int fulfilledQuantity) {
        this.fulfilledQuantity = fulfilledQuantity;
    }

    public LocalDateTime getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(LocalDateTime orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}

