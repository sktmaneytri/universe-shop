package com.hcmute.ecommerce.universeshop.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> findAll();
    CategoryEntity save(CategoryEntity category);
    CategoryEntity findById(Long id);
    CategoryEntity update(CategoryEntity category, Long id);
    void deleteById(Long id);

}
