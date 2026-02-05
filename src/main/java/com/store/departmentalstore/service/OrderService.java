
package com.store.departmentalstore.service;
import com.store.departmentalstore.exceptions.InvalidProductException;
import org.springframework.transaction.annotation.Transactional;
import com.store.departmentalstore.dto.CreateOrderItemRequestDto;
import com.store.departmentalstore.dto.CreateOrderRequestDto;
import com.store.departmentalstore.dto.OrderResponseDto;
import com.store.departmentalstore.entity.Order;
import com.store.departmentalstore.entity.OrderItem;
import com.store.departmentalstore.entity.Product;
import com.store.departmentalstore.enums.OrderStatus;
import com.store.departmentalstore.exceptions.ProductNotFoundException;
import com.store.departmentalstore.exceptions.ResourceNotFoundException;
import com.store.departmentalstore.mapper.OrderMapper;
import com.store.departmentalstore.repository.OrderRepository;
import com.store.departmentalstore.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class OrderService {


    private final ProductRepository pr;
    private final OrderRepository or;

    public OrderService(ProductRepository pr, OrderRepository or){
        this.pr = pr;
        this.or = or;
    }
    private String generateOrderId() {
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String suffix = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        return "ORD-" + date + "-" + suffix;
    }

    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequestDto request) {

        Set<Long> seen = new HashSet<>();
        for (CreateOrderItemRequestDto item : request.getItems()) {
            if (!seen.add(item.getProductId())) {
                throw new InvalidProductException("Duplicate productId in items: " + item.getProductId());
            }
        }


        Order order = new Order();
        order.setOrderId(generateOrderId());
        order.setCustomerId(request.getCustomerId());
        order.setStatus(OrderStatus.CREATED);

        for (CreateOrderItemRequestDto itemReq : request.getItems()) {
            Product product = pr.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found: " + itemReq.getProductId()));

            OrderItem item = new OrderItem();
            item.setProductId(product.getProductId());

            // snapshot fields
            item.setProductNameSnapshot(product.getProductName()); // adjust to your field name
            item.setUnitPriceSnapshot(BigDecimal.valueOf(product.getPrice())); // if price is double in Product
            item.setQuantity(itemReq.getQuantity());

            order.addItem(item);
        }

        Order saved = or.save(order);
        return OrderMapper.toDto(saved);
    }

    @Transactional()
    public OrderResponseDto getByOrderId(String orderId) {
        Order order = or.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));
        return OrderMapper.toDto(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDto> listOrders(Long customerId, int page, int size, String sortBy, String sortDir) {

        int safeSize = Math.min(Math.max(size, 1), 50);
        int safePage = Math.max(page, 0);

        Sort sort = buildSort(sortBy, sortDir);
        Pageable pageable = PageRequest.of(safePage, safeSize, sort);

        Page<Order> orders = or.findByCustomerId(customerId, pageable);
        return orders.map(OrderMapper::toDto);
    }

    private Sort buildSort(String sortBy, String sortDir) {
        // whitelist
        Set<String> allowed = Set.of("createdAt", "updatedAt", "status");
        String safeSortBy = allowed.contains(sortBy) ? sortBy : "createdAt";

        Sort.Direction direction = "asc".equalsIgnoreCase(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC;

        // stable pagination: add secondary sort on id
        return Sort.by(direction, safeSortBy).and(Sort.by(Sort.Direction.DESC, "id"));
    }

}

