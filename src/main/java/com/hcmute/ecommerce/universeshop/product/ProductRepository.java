package com.hcmute.ecommerce.universeshop.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    // Select all products
    List<ProductEntity> findAll();

    // Find product by ID
    Optional<ProductEntity> findById(Long id);

    // Find product by name\
    @Query("SELECT p FROM product p WHERE p.productName = :productName")
    Optional<ProductEntity> findByProductName(String productName);

    // Find products by manufacturer
    @Query("SELECT p FROM product p WHERE p.manufacturer = :manufacturer")
    List<ProductEntity> findByManufacturer(String manufacturer);

    // Find list of sold-out products (assuming 'ProductStatus' is an enum)
    @Query("SELECT p FROM product p WHERE p.status = :status")
    List<ProductEntity> findByStatus(ProductStatus status);

    // Find products by category name
    @Query("SELECT p FROM product p WHERE p.category.name = :categoryName")
    List<ProductEntity> findByCategory_Name(String categoryName);

    // Check if a product with the given product name exists
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM product p WHERE p.productName = :productName")
    boolean isExistsProductName(@Param("productName") String productName);

    @Query("SELECT p FROM product p WHERE p.quantity < :quantityThreshold")
    List<ProductEntity> findProductsByQuantityBelowThreshold(@Param("quantityThreshold") int quantityThreshold);
    @Override
    Page<ProductEntity> findAll(Pageable pageable);
    @Query("SELECT p FROM product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :key1, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :key2, '%'))")
    List<ProductEntity> findProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
            @Param("key1") String key1,
            @Param("key2") String key2,
            Pageable pageable
    );
}
