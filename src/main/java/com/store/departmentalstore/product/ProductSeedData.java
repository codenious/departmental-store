package com.store.departmentalstore.product;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductSeedData {

    @Bean
    public CommandLineRunner seedProducts(ProductRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }
            List<Product> products = List.of(
                new Product("MN-TSHIRT-001", "City Motion Tee",
                    "Soft-touch cotton tee with relaxed fit.", "Apparel",
                    new BigDecimal("799.00"),
                    "https://images.example.com/tees/city-motion.jpg", 120, OffsetDateTime.now()),
                new Product("MN-JKT-002", "Monochrome Bomber",
                    "Lightweight bomber jacket with ribbed collar.", "Apparel",
                    new BigDecimal("3499.00"),
                    "https://images.example.com/jackets/mono-bomber.jpg", 45, OffsetDateTime.now()),
                new Product("MN-SNK-003", "Metro Runner",
                    "Breathable knit sneakers for everyday wear.", "Footwear",
                    new BigDecimal("4299.00"),
                    "https://images.example.com/shoes/metro-runner.jpg", 60, OffsetDateTime.now()),
                new Product("MN-BAG-004", "Weekender Duffle",
                    "Carry-on friendly duffle with laptop sleeve.", "Accessories",
                    new BigDecimal("2599.00"),
                    "https://images.example.com/bags/weekender.jpg", 35, OffsetDateTime.now()),
                new Product("MN-HOME-005", "Luxe Throw Set",
                    "Premium textured throws for cozy interiors.", "Home",
                    new BigDecimal("1499.00"),
                    "https://images.example.com/home/luxe-throws.jpg", 80, OffsetDateTime.now()),
                new Product("MN-BEA-006", "Glow Serum Kit",
                    "Brightening serum trio with travel pouch.", "Beauty",
                    new BigDecimal("1899.00"),
                    "https://images.example.com/beauty/glow-serum.jpg", 70, OffsetDateTime.now())
            );
            repository.saveAll(products);
        };
    }
}
