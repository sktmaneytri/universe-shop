package com.hcmute.ecommerce.universeshop.cart.Item;

import java.util.List;

public interface CartItemService {
    CartItemEntity createItem(CartItemEntity entity);
    CartItemEntity updateItem(CartItemEntity entity);
    void deleteItem(CartItemEntity entity);
    CartItemEntity getItemById(Long id);
    List<CartItemEntity> getItems();
}
