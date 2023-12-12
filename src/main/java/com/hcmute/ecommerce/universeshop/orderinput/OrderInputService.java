package com.hcmute.ecommerce.universeshop.orderinput;

import com.hcmute.ecommerce.universeshop.cart.CartEntity;
import com.hcmute.ecommerce.universeshop.orderdetail.OrderProductQuantity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class OrderInputService {
    public OrderInput createOrderInputFromCart(CartEntity cart) {
        OrderInput orderInput = new OrderInput();
        orderInput.setFullName(cart.getUser().getUserFirstName());
        orderInput.setFullAddress(cart.getUser().getAddress());
        orderInput.setContactNumber(cart.getUser().getContactNumber());

        // Map items from the cart to OrderProductQuantity
        if (cart.getCartItem() != null || !cart.getCartItem().isEmpty()) {
            List<OrderProductQuantity> orderProductQuantities = cart.getCartItem()
                    .stream()
                    .map(cartItem -> {
                        OrderProductQuantity orderProductQuantity = new OrderProductQuantity();
                        orderProductQuantity.setCustomProductId(cartItem.getCustomProduct().getId());
                        orderProductQuantity.setQuantity(cartItem.getQuantity());
                        return orderProductQuantity;
                    })
                    .collect(Collectors.toList());
            orderInput.setOrderProductQuantityList(orderProductQuantities);

            cart.getCartItem().clear();
            cart.setTotalPrices(0.0);
            cart.setTotalItems(0);
        }
        return orderInput;
    }
}
