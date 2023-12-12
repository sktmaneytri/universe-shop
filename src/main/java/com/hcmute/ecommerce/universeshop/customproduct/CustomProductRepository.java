package com.hcmute.ecommerce.universeshop.customproduct;

import com.hcmute.ecommerce.universeshop.product.ProductEntity;
import com.hcmute.ecommerce.universeshop.product.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomProductRepository extends JpaRepository<CustomProductEntity, Long> {
    @Query("SELECT p FROM custom_product p WHERE p.user.userName = :userName")
    List<CustomProductEntity> findByUser(String userName);

    // Find list of sold-out products (assuming 'ProductStatus' is an enum)
    @Query("SELECT p FROM custom_product p WHERE p.productEntity.id = :productId")
    List<CustomProductEntity> findByProduct(Long productId);
}
