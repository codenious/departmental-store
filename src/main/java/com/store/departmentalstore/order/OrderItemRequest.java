package com.store.departmentalstore.order;

public record OrderItemRequest(
    Long productId,
    Integer quantity
) {
}
