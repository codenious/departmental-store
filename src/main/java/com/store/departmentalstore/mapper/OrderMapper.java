package com.store.departmentalstore.mapper;

import com.store.departmentalstore.dto.OrderItemResponseDto;
import com.store.departmentalstore.dto.OrderResponseDto;
import com.store.departmentalstore.entity.Order;
import com.store.departmentalstore.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {


    public static OrderResponseDto toDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(order.getOrderId());
        dto.setCustomerId(order.getCustomerId());
        dto.setStatus(order.getStatus().name());
        dto.setCreatedAt(order.getCreatedAt());

        BigDecimal total = BigDecimal.ZERO;

        List<OrderItemResponseDto> itemDtos = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            OrderItemResponseDto i = new OrderItemResponseDto();
            i.setProductId(item.getProductId());
            i.setProductName(item.getProductNameSnapshot());
            i.setUnitPrice(item.getUnitPriceSnapshot());
            i.setQuantity(item.getQuantity());

            BigDecimal line = item.getUnitPriceSnapshot().multiply(BigDecimal.valueOf(item.getQuantity()));
            i.setLineAmount(line);

            total = total.add(line);
            itemDtos.add(i);
        }

        dto.setItems(itemDtos);
        dto.setTotalAmount(total);
        return dto;
    }


}
