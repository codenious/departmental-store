package com.store.departmentalstore.catalog;

import com.store.departmentalstore.product.ProductResponse;
import java.util.List;

public record CatalogResponse(
    String headline,
    String subHeadline,
    List<String> heroBanners,
    List<String> categories,
    List<ProductResponse> featuredProducts
) {
}
