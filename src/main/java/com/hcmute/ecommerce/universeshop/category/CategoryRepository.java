package com.hcmute.ecommerce.universeshop.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    @Query("SELECT c FROM category c WHERE c.name = :name")
    CategoryEntity getCategoryByName(String name);
}
