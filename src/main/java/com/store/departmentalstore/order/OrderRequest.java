package com.store.departmentalstore.order;

import java.util.List;

public record OrderRequest(
    String customerEmail,
    String shippingAddress,
    List<OrderItemRequest> items
) {
}
