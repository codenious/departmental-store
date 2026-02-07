package com.store.departmentalstore.product;

import java.math.BigDecimal;

public record ProductSnapshot(
    Long id,
    String name,
    String sku,
    BigDecimal price,
    String imageUrl
) {
}
