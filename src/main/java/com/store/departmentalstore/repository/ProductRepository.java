
package com.store.departmentalstore.repository;

import com.store.departmentalstore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    public boolean existsByProductName(String productName);

    public void deleteByProductName(String productName);
}
