package com.hcmute.ecommerce.universeshop.cart;

import com.hcmute.ecommerce.universeshop.customproduct.CustomProductEntity;
import com.hcmute.ecommerce.universeshop.customproduct.SizeEnum;
import com.hcmute.ecommerce.universeshop.product.ProductEntity;

public interface CartService {
    CartEntity addItemToCart(CustomProductEntity customProduct, int quantity, String size);
    CartEntity getCartByUser();
    CartEntity updateItemInCart(Long productId, int quantity);
    CartEntity deleteItemInCart(Long productId);
}
