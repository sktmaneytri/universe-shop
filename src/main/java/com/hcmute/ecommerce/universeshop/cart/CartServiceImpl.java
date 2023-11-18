package com.hcmute.ecommerce.universeshop.cart;

import com.hcmute.ecommerce.universeshop.base.exception.Constants;
import com.hcmute.ecommerce.universeshop.base.exception.ErrorMessage;
import com.hcmute.ecommerce.universeshop.base.exception.InputValidationException;
import com.hcmute.ecommerce.universeshop.base.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    private final ErrorMessage errorMessage;
    private final CartRepository cartRepository;
    @Autowired
    public CartServiceImpl(ErrorMessage errorMessage, CartRepository cartRepository) {
        this.errorMessage = errorMessage;
        this.cartRepository = cartRepository;
    }
    @Override
    public CartEntity createCart(CartEntity entity) {
        return cartRepository.save(entity);
    }
    @Override
    public CartEntity updateCart(CartEntity entity, Long cartId) {
        CartEntity cartToUpdate = cartRepository.findById(cartId).get();
        if(cartToUpdate == null) {
            throw new InputValidationException(errorMessage.getMessage(Constants.CART_NOT_FOUND));
        }
        cartToUpdate.setCartItems(entity.getCartItems());
        cartToUpdate.setCartTotal(entity.getCartTotal());
        return cartRepository.save(cartToUpdate);
    }
    @Override
    public CartEntity getCartById(Long id) {
        return cartRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(errorMessage.getMessage(Constants.CART_NOT_FOUND))
        );
    }
}
