package com.store.departmentalstore.product;

import java.math.BigDecimal;

public record ProductRequest(
    String sku,
    String name,
    String description,
    String category,
    BigDecimal price,
    String imageUrl,
    Integer availableQuantity
) {
}
