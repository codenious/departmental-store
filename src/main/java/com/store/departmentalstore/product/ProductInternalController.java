package com.store.departmentalstore.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/products")
public class ProductInternalController {
    private final ProductService service;

    public ProductInternalController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ProductSnapshot fetchSnapshot(@PathVariable Long id) {
        return service.snapshotProduct(id);
    }
}
