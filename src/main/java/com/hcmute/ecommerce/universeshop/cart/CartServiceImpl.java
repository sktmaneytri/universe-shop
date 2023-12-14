package com.hcmute.ecommerce.universeshop.cart;

import com.hcmute.ecommerce.universeshop.base.Authentication.JwtRequestFilter;
import com.hcmute.ecommerce.universeshop.base.Authentication.JwtUtils;
import com.hcmute.ecommerce.universeshop.base.exception.Constants;
import com.hcmute.ecommerce.universeshop.base.exception.ErrorMessage;
import com.hcmute.ecommerce.universeshop.base.exception.InputValidationException;
import com.hcmute.ecommerce.universeshop.base.exception.ResourceNotFoundException;
import com.hcmute.ecommerce.universeshop.cart.Item.CartItemEntity;
import com.hcmute.ecommerce.universeshop.cart.Item.CartItemRepository;
import com.hcmute.ecommerce.universeshop.customproduct.CustomProductEntity;
import com.hcmute.ecommerce.universeshop.customproduct.CustomProductRepository;
import com.hcmute.ecommerce.universeshop.customproduct.CustomProductService;
import com.hcmute.ecommerce.universeshop.customproduct.SizeEnum;
import com.hcmute.ecommerce.universeshop.product.ProductEntity;
import com.hcmute.ecommerce.universeshop.user.UserEntity;
import com.hcmute.ecommerce.universeshop.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@Slf4j
public class CartServiceImpl implements CartService {

    private final ErrorMessage errorMessage;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomProductRepository customProductRepository;

    @Autowired
    public CartServiceImpl(ErrorMessage errorMessage, CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.errorMessage = errorMessage;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }
    private CartItemEntity findCartItemEntity(List<CartItemEntity> cartItems, Long customProductId) {
        if (cartItems == null) {
            return null;
        }
        return cartItems.stream()
                .filter(item -> item.getCustomProduct().getId().equals(customProductId))
                .findFirst()
                .orElse(null);
    }

    private int calculateTotalItems(List<CartItemEntity> cartItems) {
        return cartItems.stream()
                .mapToInt(CartItemEntity::getQuantity)
                .sum();
    }

    private double calculateTotalPrice(List<CartItemEntity> cartItems) {
        return cartItems.stream()
                .mapToDouble(CartItemEntity::getTotalPrice)
                .sum();
    }

    @Override
    public CartEntity addItemToCart(CustomProductEntity customProduct, int quantity, String size) {
        String username = JwtRequestFilter.CURRENT_USER;
        UserEntity user = userRepository.findById(username).orElseThrow(
                () -> new ResourceNotFoundException(errorMessage.getMessage(Constants.USER_NOT_FOUND))
        );

        CartEntity cart = user.getCart();
        if (cart == null) {
            cart = new CartEntity();
            cart.setUser(user);
            cart.setCartItem(new ArrayList<>()); // Initialize cart items list
            cart.setTotalItems(0);
            cart.setTotalPrices(0.0);
        }

        List<CartItemEntity> cartItems = cart.getCartItem();
        CartItemEntity cartItem = findCartItemEntity(cartItems, customProduct.getId());

        if (cartItem == null) {
            cartItem = new CartItemEntity();
            cartItem.setCustomProduct(customProduct);
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(calculateTotalPrice(quantity, customProduct.getProductEntity().getActualPrice()));
            cartItem.setCart(cart);
            cartItems.add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice(calculateTotalPrice(cartItem.getQuantity(), customProduct.getProductEntity().getActualPrice()));
        }

        // Update totalPrices and totalItems at the CartEntity level
        cart.setTotalItems(calculateTotalItems(cartItems));
        cart.setTotalPrices(calculateTotalPrice(cartItems));

        cartRepository.save(cart);
        user.setCart(cart);
        userRepository.save(user);
        customProduct.setSize(SizeEnum.valueOf(size));
        customProductRepository.save(customProduct);
        return cart;
    }

    @Override
    public CartEntity getCartByUser() {
        String username = JwtRequestFilter.CURRENT_USER;
        UserEntity user = userRepository.findById(username).orElseThrow(
                () -> new ResourceNotFoundException(errorMessage.getMessage(Constants.USER_NOT_FOUND))
        );

        log.info("User found: {}", user.getUserName());

        CartEntity cart = user.getCart();

        if (cart != null) {
            log.info("Cart found for user: {}", cart.getId());
        } else {
            log.info("No cart found for user");
        }
        return cart;
    }
    private double calculateTotalPrice(int quantity, double price) {
        if (quantity < 0 || price < 0) {
            throw new IllegalArgumentException("Quantity and price must be non-negative");
        }
        // Use BigDecimal for precise calculations involving currency
        BigDecimal quantityBigDecimal = BigDecimal.valueOf(quantity);
        BigDecimal priceBigDecimal = BigDecimal.valueOf(price);
        BigDecimal result = quantityBigDecimal.multiply(priceBigDecimal);

        if (result.compareTo(BigDecimal.valueOf(Double.MAX_VALUE)) > 0) {
            throw new ArithmeticException("Total price exceeds the maximum value for a double");
        }

        return result.doubleValue();
    }

    @Override
    public CartEntity updateItemInCart(Long customProductId, int quantity) {
        String username = JwtRequestFilter.CURRENT_USER;
        UserEntity user = userRepository.findById(username).orElseThrow(
                () -> new ResourceNotFoundException(errorMessage.getMessage(Constants.USER_NOT_FOUND))
        );

        CartEntity cart = user.getCart();
        if (cart == null) {
            throw new ResourceNotFoundException(errorMessage.getMessage(Constants.CART_NOT_FOUND));
        }
        List<CartItemEntity> cartItems = cart.getCartItem();
        CartItemEntity cartItem = findCartItemEntity(cartItems, customProductId);

        if (cartItem == null) {
            throw new ResourceNotFoundException(errorMessage.getMessage(Constants.CART_ITEM_NOT_FOUND));
        }
        if (quantity == 0) {
            cartItems.remove(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(calculateTotalPrice(quantity, cartItem.getCustomProduct().getProductEntity().getActualPrice()));
        }
        // Update totalPrices and totalItems at the CartEntity level
        cart.setTotalItems(calculateTotalItems(cartItems));
        cart.setTotalPrices(calculateTotalPrice(cartItems));

        cartRepository.save(cart);
        user.setCart(cart);
        userRepository.save(user);
        return cart;
    }

    @Override
    public CartEntity deleteItemInCart(Long productId) {
        String username = JwtRequestFilter.CURRENT_USER;
        UserEntity user = userRepository.findById(username).orElseThrow(
                () -> new ResourceNotFoundException(errorMessage.getMessage(Constants.USER_NOT_FOUND))
        );

        CartEntity cart = user.getCart();
        if (cart == null) {
            throw new ResourceNotFoundException(errorMessage.getMessage(Constants.CART_NOT_FOUND));
        }

        List<CartItemEntity> cartItems = cart.getCartItem();
        CartItemEntity cartItem = findCartItemEntity(cartItems, productId);

        if (cartItem == null) {
            throw new ResourceNotFoundException(errorMessage.getMessage(Constants.CART_ITEM_NOT_FOUND));
        }
        cartItems.remove(cartItem);
        // Update totalPrices and totalItems at the CartEntity level
        cart.setTotalItems(calculateTotalItems(cartItems));
        cart.setTotalPrices(calculateTotalPrice(cartItems));

        cartRepository.save(cart);
        user.setCart(cart);
        userRepository.save(user);
        return cart;
    }
}
