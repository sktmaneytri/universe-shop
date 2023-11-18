package com.hcmute.ecommerce.universeshop.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    public CategoryEntity findByName(String name);
    @Query("SELECT c FROM CategoryEntity c WHERE c.name =: name AND c.parentCategory.name =: parentCategoryName")
    public CategoryEntity findByNameAndParant(@Param("name") String name,
                                              @Param("parantCategoryName") String parentCategoryName);
}
