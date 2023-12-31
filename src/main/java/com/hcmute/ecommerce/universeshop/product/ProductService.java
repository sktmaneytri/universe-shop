package com.hcmute.ecommerce.universeshop.product;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    // Retrieve all products
    void initProducts();
    List<ProductDto> getAllProducts(Pageable pageable, String key);

    // Retrieve product by ID
    ProductDto getProductById(Long productId);

    // Retrieve product by name
    ProductDto getProductByName(String productName);

    // Retrieve products by manufacturer
    List<ProductDto> getProductsByManufacturer(String manufacturer);

    // Retrieve products that are sold out
    List<ProductDto> getSoldOutProducts();

    // Retrieve products by category name
    List<ProductDto> getProductsByCategoryName(String categoryName);

    // Retrieve products with quantity below a certain threshold
    List<ProductDto> getProductsByQuantityBelowThreshold(int quantityThreshold);

    // Create a new product
    ProductEntity createProduct(ProductEntity productEntity);
    ProductDto setImageProduct(List<MultipartFile> files, Long productId) throws IOException;

    // Update an existing product
    ProductDto updateProduct(Long productId, ProductEntity productEntity);

    // Delete a product by ID
    void deleteProductById(Long productId);
}
