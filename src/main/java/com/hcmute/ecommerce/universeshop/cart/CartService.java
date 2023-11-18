package com.hcmute.ecommerce.universeshop.cart;

public interface CartService {
    CartEntity createCart (CartEntity entity);
    CartEntity updateCart (CartEntity entity, Long cartId);
    CartEntity getCartById(Long id);
}
