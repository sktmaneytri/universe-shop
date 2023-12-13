package com.hcmute.ecommerce.universeshop.customproduct;

import com.hcmute.ecommerce.universeshop.base.utils.FirebaseUtils;
import com.hcmute.ecommerce.universeshop.cart.CartEntity;
import com.hcmute.ecommerce.universeshop.image.ImageRepository;
import com.hcmute.ecommerce.universeshop.product.ProductDto;
import com.hcmute.ecommerce.universeshop.product.ProductEntity;
import com.hcmute.ecommerce.universeshop.product.ProductRepository;
import com.hcmute.ecommerce.universeshop.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class CustomProductResource {
    @Autowired
    private CustomProductService customProductService;

    @PostMapping("/{proId}/custom-products")
    public ResponseEntity<CustomProductEntity> saveCustomProduct(@PathVariable Long proId , @RequestParam("images") List<MultipartFile> multipartFiles) throws IOException {
        CustomProductEntity createdCustomProductEntity = customProductService.saveCustomProduct(proId, multipartFiles);
        return new ResponseEntity<>(createdCustomProductEntity, HttpStatus.CREATED);
    }

    @GetMapping("/custom-products/{proId}")
    public ResponseEntity<CustomProductEntity> getCustomProductByID(@PathVariable Long proId) {
        CustomProductEntity createdCustomProductEntity = customProductService.findCustomProductById(proId);
        return new ResponseEntity<>(createdCustomProductEntity, HttpStatus.OK);
    }
}
