package com.hcmute.ecommerce.universeshop.cart.Item;

import com.hcmute.ecommerce.universeshop.base.exception.Constants;
import com.hcmute.ecommerce.universeshop.base.exception.ErrorMessage;
import com.hcmute.ecommerce.universeshop.base.exception.InputValidationException;
import com.hcmute.ecommerce.universeshop.base.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService{

    private final CartItemRepository cartItemRepository;
    private final ErrorMessage errorMessage;
    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository, ErrorMessage errorMessage) {
        this.cartItemRepository = cartItemRepository;
        this.errorMessage = errorMessage;
    }

    @Override
    public CartItemEntity createItem(CartItemEntity entity) {
        if(entity.getQuantity() < 1) {
           throw new InputValidationException(errorMessage.getMessage(Constants.INVALID_INPUT_VALUE));
        }
        return cartItemRepository.save(entity);
    }

    @Override
    public CartItemEntity updateItem(CartItemEntity entity) {
        return null;
    }

    @Override
    public void deleteItem(CartItemEntity entity) {
        if(cartItemRepository.findById(entity.getId()) == null) {
            throw new InputValidationException(errorMessage.getMessage(Constants.CATEGORY_NOT_FOUND));
        }
        cartItemRepository.deleteById(entity.getId());
    }

    @Override
    public CartItemEntity getItemById(Long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage.getMessage(Constants.CATEGORY_NOT_FOUND)));
    }
    @Override
    public List<CartItemEntity> getItems() {
        return cartItemRepository.findAll();
    }
}
