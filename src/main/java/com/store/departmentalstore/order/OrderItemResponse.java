package com.store.departmentalstore.order;

import java.math.BigDecimal;

public record OrderItemResponse(
    Long productId,
    String productName,
    String productSku,
    String imageUrl,
    BigDecimal unitPrice,
    Integer quantity,
    BigDecimal lineTotal
) {
}
