package com.hcmute.ecommerce.universeshop.category;

import java.util.List;

public interface CategoryService {

    void initCategories();
    List<CategoryEntity> findAll();
    CategoryEntity save(CategoryEntity category);
    CategoryEntity findById(Long id);
    CategoryEntity update(CategoryEntity category, Long id);
    void deleteById(Long id);

}
