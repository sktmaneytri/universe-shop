package com.hcmute.ecommerce.universeshop.cart;

import com.hcmute.ecommerce.universeshop.customer.CustomerEntity;
import com.hcmute.ecommerce.universeshop.product.ProductEntity;

public interface CartService {
    CartEntity addItemToCart(ProductEntity productEntity, int quantity, String token);

    CartEntity updateItemInCart(ProductEntity productEntity, int quantity, String token);
    CartEntity deleteItemInCart(ProductEntity productEntity, String token);
}
