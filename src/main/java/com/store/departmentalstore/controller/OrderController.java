
package com.store.departmentalstore.controller;

import com.store.departmentalstore.entity.Order;
import com.store.departmentalstore.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Order placeOrder(@RequestParam Long productId,
                            @RequestParam Long customerId,
                            @RequestParam int quantity) {
        return service.placeOrder(productId, customerId, quantity);
    }

    @PostMapping("/inventory/replenish")
    public void replenish(@RequestParam Long productId,
                          @RequestParam int quantity) {
        service.replenishInventory(productId, quantity);
    }

}
