package com.store.departmentalstore.order;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record OrderResponse(
    String orderNumber,
    String customerEmail,
    String shippingAddress,
    OrderStatus status,
    BigDecimal totalAmount,
    OffsetDateTime createdAt,
    List<OrderItemResponse> items
) {
}
