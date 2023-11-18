package com.hcmute.ecommerce.universeshop.product;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public ProductEntity createProduct(CreateProductRequest productRequest);

    public String deleteProduct(Long productId) throws ProductException;

    public ProductEntity updateProduct(Long productId, ProductEntity productRequest)
            throws ProductException;

    public ProductEntity findProductById(Long id) throws ProductException;

    public List<ProductEntity> findProductByCategory(String category);

    public Page<ProductEntity> getAllProduct(String category, List<String> colors, List<String> sizes,
                                             Integer minPrice, Integer maxPrice, Integer minDiscount,
                                             String sort, String stock, Integer pageNumber, Integer pageSize);

}