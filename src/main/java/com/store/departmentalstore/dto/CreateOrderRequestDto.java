package com.store.departmentalstore.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateOrderRequestDto {

    @NotNull(message = "customerId is required")
        private Long customerId;

        @NotEmpty(message =  "Item cannot be empty")
        @Valid
        private List<CreateOrderItemRequestDto> items;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<CreateOrderItemRequestDto> getItems() {
        return items;
    }

    public void setItems(List<CreateOrderItemRequestDto> items) {
        this.items = items;
    }


}
