package com.hcmute.ecommerce.universeshop.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductResource {

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
        ProductDto product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/name/{productName}")
    public ResponseEntity<ProductDto> getProductByName(@PathVariable String productName) {
        ProductDto product = productService.getProductByName(productName);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/manufacturer/{manufacturer}")
    public ResponseEntity<List<ProductDto>> getProductsByManufacturer(@PathVariable String manufacturer) {
        List<ProductDto> products = productService.getProductsByManufacturer(manufacturer);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/sold-out")
    public ResponseEntity<List<ProductDto>> getSoldOutProducts() {
        List<ProductDto> products = productService.getSoldOutProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ProductDto>> getProductsByCategoryName(@PathVariable String categoryName) {
        List<ProductDto> products = productService.getProductsByCategoryName(categoryName);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity productEntity) {
        ProductEntity createdProduct = productService.createProduct(productEntity);
        URI uri = URI.create("/api/v1/products/" + createdProduct.getId());
        return ResponseEntity.created(uri).body(createdProduct);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(productId, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }
}