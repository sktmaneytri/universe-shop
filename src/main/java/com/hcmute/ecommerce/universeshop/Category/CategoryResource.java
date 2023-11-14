package com.hcmute.ecommerce.universeshop.Category;

import com.hcmute.ecommerce.universeshop.base.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryResource {

    private final CategoryService categoryService;
    @Autowired
    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping
    public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        List<CategoryEntity> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryEntity> getCategoryById(@PathVariable Long categoryId) {
        CategoryEntity category = categoryService.findById(categoryId);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryEntity category) {
        CategoryEntity createdCategory = categoryService.save(category);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/categories/" + category.getId()).toUriString());
        return ResponseEntity.created(uri).body(createdCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteById(categoryId);
        return ResponseEntity.ok("Category " + categoryId + " has been deleted successfully!");
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryEntity> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryEntity category) {
        CategoryEntity updatedCategory = categoryService.update(category, categoryId);
        return ResponseEntity.ok(updatedCategory);
    }

}
