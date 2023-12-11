package com.hcmute.ecommerce.universeshop.product;

import com.hcmute.ecommerce.universeshop.category.CategoryService;
import com.hcmute.ecommerce.universeshop.image.ImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductResource {

    private final ProductService productService;

    private final CategoryService categoryService;

    @Autowired
    public ProductResource(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }
    @PostConstruct
    public void initCategoriesAndProducts() {
        categoryService.initCategories();
        productService.initProducts();
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "9") int limit,
                                                           @RequestParam(defaultValue = "") String searchKey) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        List<ProductDto> products = productService.getAllProducts(pageRequest, searchKey);
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

    @PostMapping("/{id}/set-images")
    public ResponseEntity<ProductDto> setImages(@PathVariable Long id , @RequestParam("images") List<MultipartFile> multipartFiles) throws IOException {
        ProductDto product = productService.setImageProduct(multipartFiles, id);
        return ResponseEntity.ok(product);
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

    @PostMapping()
    public ResponseEntity<ProductEntity> createProduct(@RequestPart("product") ProductEntity productEntity) {
            ProductEntity createdProduct = productService.createProduct(productEntity);
            URI uri = URI.create("/api/v1/products/" + createdProduct.getId());
            return ResponseEntity.created(uri).body(createdProduct);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @RequestBody ProductEntity productEntity) {
        ProductDto updatedProduct = productService.updateProduct(productId, productEntity);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }
}