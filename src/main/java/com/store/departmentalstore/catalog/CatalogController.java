package com.store.departmentalstore.catalog;

import com.store.departmentalstore.product.ProductResponse;
import com.store.departmentalstore.product.ProductService;
import java.util.List;
import java.util.Optional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {
    private final ProductService productService;

    public CatalogController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/landing")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public CatalogResponse getLanding() {
        List<ProductResponse> featured = productService.fetchProducts(Optional.empty())
            .stream()
            .limit(6)
            .toList();
        return new CatalogResponse(
            "Mnytra-inspired Departmental Store",
            "Seasonal drops, premium basics, and fast delivery.",
            List.of("Festive Wear Edit", "Streetwear Essentials", "Home & Living Luxe"),
            List.of("Apparel", "Footwear", "Beauty", "Accessories", "Home", "Kids"),
            featured
        );
    }
}
