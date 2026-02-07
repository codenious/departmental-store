package com.store.departmentalstore.order;

import com.store.departmentalstore.product.ProductSnapshot;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
    private final OrderRepository repository;
    private final ProductServiceClient productServiceClient;

    public OrderService(OrderRepository repository, ProductServiceClient productServiceClient) {
        this.repository = repository;
        this.productServiceClient = productServiceClient;
    }

    public List<OrderResponse> listOrders(Optional<String> customerEmail) {
        List<Order> orders = customerEmail
            .map(repository::findByCustomerEmailIgnoreCase)
            .orElseGet(repository::findAll);
        return orders.stream().map(this::toResponse).toList();
    }

    public OrderResponse createOrder(OrderRequest request) {
        if (request.items() == null || request.items().isEmpty()) {
            throw new IllegalArgumentException("Order must include at least one item.");
        }
        String orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Order order = new Order(orderNumber, request.customerEmail(), request.shippingAddress(),
            OrderStatus.CREATED, BigDecimal.ZERO, OffsetDateTime.now());
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemRequest itemRequest : request.items()) {
            ProductSnapshot product = productServiceClient.fetchProduct(itemRequest.productId());
            BigDecimal lineTotal = product.price().multiply(BigDecimal.valueOf(itemRequest.quantity()));
            total = total.add(lineTotal);
            OrderItem item = new OrderItem(
                product.id(),
                product.name(),
                product.sku(),
                product.imageUrl(),
                product.price(),
                itemRequest.quantity()
            );
            order.addItem(item);
        }
        order.setTotalAmount(total);
        return toResponse(repository.save(order));
    }

    public OrderResponse fetchOrder(String orderNumber) {
        return repository.findByOrderNumber(orderNumber)
            .map(this::toResponse)
            .orElseThrow(() -> new OrderNotFoundException(orderNumber));
    }

    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
            .map(item -> new OrderItemResponse(
                item.getProductId(),
                item.getProductName(),
                item.getProductSku(),
                item.getImageUrl(),
                item.getUnitPrice(),
                item.getQuantity(),
                item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
            ))
            .toList();
        return new OrderResponse(
            order.getOrderNumber(),
            order.getCustomerEmail(),
            order.getShippingAddress(),
            order.getStatus(),
            order.getTotalAmount(),
            order.getCreatedAt(),
            itemResponses
        );
    }
}
