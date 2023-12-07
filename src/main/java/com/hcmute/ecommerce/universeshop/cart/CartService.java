package com.hcmute.ecommerce.universeshop.cart;

import com.hcmute.ecommerce.universeshop.product.ProductEntity;

public interface CartService {
    CartEntity addItemToCart(ProductEntity productEntity, int quantity);
    CartEntity getCartByUser();
    CartEntity updateItemInCart(Long productId, int quantity);
    CartEntity deleteItemInCart(Long productId);
}
