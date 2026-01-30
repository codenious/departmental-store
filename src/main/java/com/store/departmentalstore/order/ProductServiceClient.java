package com.store.departmentalstore.order;

import com.store.departmentalstore.product.ProductSnapshot;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${store.services.product.base-url}")
public interface ProductServiceClient {

    @GetMapping("/internal/products/{id}")
    ProductSnapshot fetchProduct(@PathVariable("id") Long id);
}
