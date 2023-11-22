package com.hcmute.ecommerce.universeshop.cart;

import com.hcmute.ecommerce.universeshop.base.Authentication.JwtUtils;
import com.hcmute.ecommerce.universeshop.base.exception.Constants;
import com.hcmute.ecommerce.universeshop.base.exception.ErrorMessage;
import com.hcmute.ecommerce.universeshop.base.exception.InputValidationException;
import com.hcmute.ecommerce.universeshop.base.exception.ResourceNotFoundException;
import com.hcmute.ecommerce.universeshop.cart.Item.CartItemEntity;
import com.hcmute.ecommerce.universeshop.cart.Item.CartItemRepository;
import com.hcmute.ecommerce.universeshop.customer.CustomerEntity;
import com.hcmute.ecommerce.universeshop.product.ProductEntity;
import com.hcmute.ecommerce.universeshop.user.UserEntity;
import com.hcmute.ecommerce.universeshop.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final ErrorMessage errorMessage;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public CartServiceImpl(ErrorMessage errorMessage, CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.errorMessage = errorMessage;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }
//    @Override
//    public CartEntity addItemToCart(ProductEntity productEntity, int quantity) {
//        CartEntity cart = customer.getCustomerCart();
//        if (cart == null) {b
//            cart = new CartEntity();
//            cart.setCustomer(customer);
//        }
//
//        Set<CartItemEntity> cartItems = cart.getCartItem();
//        CartItemEntity cartItem = findCartItemEntity(cartItems, productEntity.getId());
//
//        if (cartItem == null) {
//            cartItem = new CartItemEntity();
//            cartItem.setProduct(productEntity);
//            cartItem.setQuantity(quantity);
//            cartItem.setTotalPrice(quantity * productEntity.getActualPrice());
//            cartItem.setCart(cart);
//            cartItems.add(cartItem);
//            cartItemRepository.save(cartItem);
//        } else {
//            cartItem.setQuantity(cartItem.getQuantity() + quantity);
//            cartItem.setTotalPrice(cartItem.getTotalPrice() + (quantity * productEntity.getActualPrice()));
//            cartItemRepository.save(cartItem);
//        }
//
//        cart.setCartItem(cartItems);
//
//        int totalItems = calculateTotalItems(cartItems);
//        double totalPrice = calculateTotalPrice(cartItems);
//        cart.setTotalItems(totalItems);
//        cart.setTotalPrices(totalPrice);
//
//        return cartRepository.save(cart);
//    }


    private CartItemEntity findCartItemEntity(Set<CartItemEntity> cartItems, Long productId) {
        if (cartItems == null) {
            return null;
        }
        return cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    private int calculateTotalItems(Set<CartItemEntity> cartItems) {
        return cartItems.stream()
                .mapToInt(CartItemEntity::getQuantity)
                .sum();
    }

    private double calculateTotalPrice(Set<CartItemEntity> cartItems) {
        return cartItems.stream()
                .mapToDouble(CartItemEntity::getTotalPrice)
                .sum();
    }

    @Override
    public CartEntity addItemToCart(ProductEntity productEntity, int quantity, String token) {
        return null;
    }

    @Override
    public CartEntity updateItemInCart(ProductEntity productEntity, int quantity, String token) {
        return null;
    }

    @Override
    public CartEntity deleteItemInCart(ProductEntity productEntity, String token) {
        return null;
    }
}
