package com.hcmute.ecommerce.universeshop.orderdetail;

import com.hcmute.ecommerce.universeshop.orderinput.OrderInput;
import com.hcmute.ecommerce.universeshop.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderDetailResource {
    @Autowired
    private OrderDetailService orderDetailService;
    @PostMapping({"/placeOrder"})
    @PreAuthorize("hasRole('USER')")
    public void placeOrder(@RequestBody OrderInput orderInput) {
        orderDetailService.placeOrder(orderInput);
    }
}
