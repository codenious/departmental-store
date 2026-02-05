
package com.store.departmentalstore.controller;

import com.store.departmentalstore.dto.CreateOrderRequestDto;
import com.store.departmentalstore.dto.OrderResponseDto;
import com.store.departmentalstore.entity.Order;
import com.store.departmentalstore.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody CreateOrderRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(request));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderByOrderId(@PathVariable String orderId) {
        return ResponseEntity.ok(service.getByOrderId(orderId));
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponseDto>> listOrders(
            @RequestParam @NotNull(message="customerId is required") Long customerId,
            @RequestParam(defaultValue="0") @Min(value=0, message="page must be >= 0") int page,
            @RequestParam(defaultValue="10") @Min(value=1, message="size must be >= 1") @Max(value=50, message="size must be <= 50") int size,
            @RequestParam(defaultValue="createdAt") String sortBy,
            @RequestParam(defaultValue="desc") String sortDir
    ) {
        return ResponseEntity.ok(service.listOrders(customerId, page, size, sortBy, sortDir));
    }
}

