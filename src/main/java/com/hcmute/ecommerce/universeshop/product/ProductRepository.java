package com.hcmute.ecommerce.universeshop.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query("SELECT p FROM ProductEntity p" +
            "WHERE (p.category.name =: category OR :category='')" +
            "AND ((:minPrice IS NULL AND :maxprice IS NULL) OR (p.discountPrice BETWEEN :minPrice AND :maxPrice))" +
            "ORDER BY" +
            "CASE WHEN :sort='price_low' THEN p.discountPrice END ASC," +
            "CASE WHEN :sort='price_high' THEN p.discountPrice END DESC"
    )
    public List<ProductEntity> filterProducts(@Param("category") String category,
                                              @Param("minPrice") Integer minPrice,
                                              @Param("maxPrice") Integer maxPrice,
                                              @Param("minDiscount") Integer minDiscount,
                                              @Param("sort") String sort);
}