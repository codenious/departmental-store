package com.store.departmentalstore.service;


import com.store.departmentalstore.dto.ProductRequestDto;
import com.store.departmentalstore.dto.ProductResponseDto;
import com.store.departmentalstore.dto.ProductUpdateRequestDto;
import com.store.departmentalstore.entity.Product;
import com.store.departmentalstore.exceptions.InvalidProductException;
import com.store.departmentalstore.exceptions.ProductNotFoundException;
import com.store.departmentalstore.mapper.ProductMapper;
import com.store.departmentalstore.repository.OrderRepository;
import com.store.departmentalstore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository pr;
    private final OrderRepository or;

    public ProductService(ProductRepository productRepository, OrderRepository or){
        this.pr = productRepository;
        this.or = or;
    }

    public List<ProductResponseDto> getAllProducts(){
        List<Product> productListEntity = pr.findAll();
        List<ProductResponseDto> productList = new ArrayList<>();
        for (Product product : productListEntity){
            productList.add(ProductMapper.responseDto(product));
        }

        return productList;
    }

    public ProductResponseDto addProduct(ProductRequestDto request){

        validateProduct(request);
        Product newProduct = ProductMapper.toEntity(request);
        Product savedProduct = pr.save(newProduct);
        return ProductMapper.responseDto(savedProduct);

    }

    public void deleteProductByName(String productName){
        String name = productName.trim().toLowerCase();
        if(!pr.existsByProductName(name)) {
            throw new ProductNotFoundException("Product not found with name: " + name);
        }
        pr.deleteByProductName(name);
    }

    public void deleteByProductId(Long productId){
        Product product = pr.findById(productId).orElseThrow(() ->
                new ProductNotFoundException("Product not found with ID: " + productId));
        pr.deleteById(productId);
    }

    public void updateProduct(Long productId, ProductUpdateRequestDto request) {

        Product product = pr.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id: " + productId));

        if (request.getProductName() != null) {
            validateProductName(request.getProductName());
            product.setProductName(request.getProductName());
        }

        if (request.getProductDesc() != null) {
            validateProductDesc(request.getProductDesc());
            product.setProductDesc(request.getProductDesc());
        }

        if (request.getPrice() != null) {
            validatePrice(request.getPrice());
            product.setPrice(request.getPrice());
        }

        if (request.getExpiry() != null) {
            validateExpiry(request.getExpiry());
            product.setExpiry(request.getExpiry());
        }

        if (request.getCount() != null) {
            validateCount(request.getCount());
            product.setCount(request.getCount());
        }

        if (request.getAvailability() != null) {
            product.setAvailability(request.getAvailability());
        }
    }

        // -------- Validation Methods --------

        private void validateProductName(String name) {
            if (name.length() > 30) {
                throw new InvalidProductException("Product name must be under 30 characters");
            }
        }

        private void validateProductDesc(String desc) {
            if (desc.length() > 250) {
                throw new InvalidProductException("Product description must be under 250 characters");
            }
        }

        private void validatePrice(Double price) {
            if (price < 0) {
                throw new InvalidProductException("Price cannot be negative");
            }
        }

        private void validateExpiry(LocalDate expiry) {
            if (!expiry.isAfter(LocalDate.now())) {
                throw new InvalidProductException("Expiry must be after today");
            }
        }

        private void validateCount(Integer count) {
            if (count < 0) {
                throw new InvalidProductException("Count cannot be negative");
            }
        }

    private void validateProduct(ProductRequestDto request){

        if(request.getProductName() == null || request.getProductName().length() > 50) throw new InvalidProductException("Product name should not be null and more than 30 characters");
        if(request.getProductDesc() == null || request.getProductDesc().length() > 250) throw new InvalidProductException("Product Desc should not be null and more than 250 characters");
        if(request.getPrice() < 0) throw new InvalidProductException("Price cannot be NULL and less than 0");
        if(request.getExpiry() == null || !request.getExpiry().isAfter(LocalDate.now())) throw new InvalidProductException("Expiry Date must be after today");
        if(request.getCount()<0) throw new InvalidProductException("Count cannot be zero");


    }
}





