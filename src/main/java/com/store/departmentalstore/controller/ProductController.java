package com.store.departmentalstore.controller;


import com.store.departmentalstore.dto.ProductRequestDto;
import com.store.departmentalstore.dto.ProductResponseDto;
import com.store.departmentalstore.dto.ProductUpdateRequestDto;
import com.store.departmentalstore.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }


    @GetMapping("/allProducts")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        List<ProductResponseDto> productList = productService.getAllProducts();
        return ResponseEntity.ok(productList);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductRequestDto productRequestDto){
        ProductResponseDto response = productService.addProduct(productRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @DeleteMapping("/delete/id/{productId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long productId){
        productService.deleteByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete/name/{productName}")
    public ResponseEntity<Void> deleteByName(@PathVariable String productName){
        productService.deleteProductByName(productName);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductUpdateRequestDto request) {

        productService.updateProduct(productId, request);
        return ResponseEntity.noContent().build();
    }
    
}
