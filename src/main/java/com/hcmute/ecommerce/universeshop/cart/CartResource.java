package com.hcmute.ecommerce.universeshop.cart;

import com.hcmute.ecommerce.universeshop.base.exception.ResourceNotFoundException;
import com.hcmute.ecommerce.universeshop.product.ProductEntity;
import com.hcmute.ecommerce.universeshop.product.ProductRepository;
import com.hcmute.ecommerce.universeshop.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
public class CartResource {

    private final CartService cartService;
    private final ProductRepository productRepository;
    @Autowired
    public CartResource(CartService cartService, ProductRepository productRepository) {
        this.cartService = cartService;
        this.productRepository = productRepository;
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addItemToCart(@RequestBody AddToCartRequest addToCartRequest) {
        try {
            // Assume AddToCartRequest has fields productId and quantity
            Long productId = addToCartRequest.getProductId();
            int quantity = addToCartRequest.getQuantity();

            // Assume you have a way to retrieve the ProductEntity based on the productId
            ProductEntity productEntity = productRepository.findById(productId).get();

            // Call the service method to add the item to the cart
            CartEntity updatedCart = cartService.addItemToCart(productEntity, quantity);

            return ResponseEntity.ok("Item added to cart successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add item to cart: " + e.getMessage());
        }
    }
    @PutMapping("/update-item")
    public ResponseEntity<CartEntity> updateItemInCart(@RequestParam Long productId, @RequestParam(defaultValue = "0") int quantity) {
            CartEntity updatedCart = cartService.updateItemInCart(productId, quantity);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }
    @GetMapping("/view-cart")
    public ResponseEntity<CartEntity> getCartByUser() {
        return ResponseEntity.ok(cartService.getCartByUser());
    }
}
