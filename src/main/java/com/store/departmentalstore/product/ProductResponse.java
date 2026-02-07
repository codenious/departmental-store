package com.store.departmentalstore.product;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ProductResponse(
    Long id,
    String sku,
    String name,
    String description,
    String category,
    BigDecimal price,
    String imageUrl,
    Integer availableQuantity,
    OffsetDateTime createdAt
) {
}
