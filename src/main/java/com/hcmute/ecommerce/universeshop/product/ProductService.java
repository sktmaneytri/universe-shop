package com.hcmute.ecommerce.universeshop.product;

public interface ProductService {
    public ProductEntity createProduct(CreateProductRequest productRequest);

    public String deleteProduct(Long productId);

}
