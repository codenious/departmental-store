package com.store.departmentalstore.mapper;

import com.store.departmentalstore.dto.ProductRequestDto;
import com.store.departmentalstore.dto.ProductResponseDto;
import com.store.departmentalstore.entity.Product;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductMapper {



    public static Product toEntity(ProductRequestDto productRequestDto){

        if(productRequestDto == null) return null;
        Product product = new Product();
        product.setAvailability(Boolean.TRUE);
        product.setExpiry(productRequestDto.getExpiry());
        product.setProductDesc(productRequestDto.getProductDesc().trim().toLowerCase());
        product.setProductName(productRequestDto.getProductName().trim().toLowerCase());
        product.setCount(productRequestDto.getCount());
        product.setPrice(productRequestDto.getPrice());
        //product.setProductInternalID(); Do not have to set this one because DB will increase it automatically
        return product;
    }

    public static ProductResponseDto responseDto(Product product){
        ProductResponseDto dto = new ProductResponseDto();
        dto.setAvailability(product.isAvailability());
        dto.setExpiry(product.getExpiry());
        dto.setProductName(product.getProductName());
        dto.setPrice(product.getPrice());
        dto.setProductID(product.getProductCode());
        return dto;

    }
}
