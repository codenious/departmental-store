package com.store.departmentalstore.product;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductResponse> fetchProducts(Optional<String> category) {
        List<Product> products = category
            .map(repository::findByCategoryIgnoreCase)
            .orElseGet(repository::findAll);
        return products.stream().map(this::toResponse).toList();
    }

    public ProductResponse fetchProduct(Long id) {
        return repository.findById(id)
            .map(this::toResponse)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product(
            request.sku(),
            request.name(),
            request.description(),
            request.category(),
            request.price(),
            request.imageUrl(),
            request.availableQuantity(),
            OffsetDateTime.now()
        );
        return toResponse(repository.save(product));
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = repository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
        product.setSku(request.sku());
        product.setName(request.name());
        product.setDescription(request.description());
        product.setCategory(request.category());
        product.setPrice(request.price());
        product.setImageUrl(request.imageUrl());
        product.setAvailableQuantity(request.availableQuantity());
        return toResponse(product);
    }

    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public ProductSnapshot snapshotProduct(Long id) {
        Product product = repository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
        return new ProductSnapshot(
            product.getId(),
            product.getName(),
            product.getSku(),
            product.getPrice(),
            product.getImageUrl()
        );
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getSku(),
            product.getName(),
            product.getDescription(),
            product.getCategory(),
            product.getPrice(),
            product.getImageUrl(),
            product.getAvailableQuantity(),
            product.getCreatedAt()
        );
    }
}
