package com.hcmute.ecommerce.universeshop.orderdetail;

import com.hcmute.ecommerce.universeshop.base.Authentication.JwtRequestFilter;
import com.hcmute.ecommerce.universeshop.base.exception.Constants;
import com.hcmute.ecommerce.universeshop.base.exception.ErrorMessage;
import com.hcmute.ecommerce.universeshop.base.exception.ResourceNotFoundException;
import com.hcmute.ecommerce.universeshop.orderinput.OrderInput;
import com.hcmute.ecommerce.universeshop.product.ProductDto;
import com.hcmute.ecommerce.universeshop.product.ProductEntity;
import com.hcmute.ecommerce.universeshop.product.ProductRepository;
import com.hcmute.ecommerce.universeshop.user.UserEntity;
import com.hcmute.ecommerce.universeshop.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {
    private static final String ORDER_PLACED = "Placed";
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ErrorMessage errorMessage;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public void placeOrder(OrderInput orderInput) {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();
        for (OrderProductQuantity o : productQuantityList) {
            ProductEntity product = productRepository.findById(o.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
            String currentUser = JwtRequestFilter.CURRENT_USER;
            UserEntity user = userRepository.findById(currentUser).orElseThrow(() -> new ResourceNotFoundException(errorMessage.getMessage(Constants.USER_NOT_FOUND)));

            OrderDetail orderDetail = OrderDetail.builder()
                    .orderFullName(orderInput.getFullName())
                    .orderFullAddress(orderInput.getFullAddress())
                    .orderContactNumber(orderInput.getContactNumber())
                    .orderAlternateContactNumber(orderInput.getAlternateContactNumber())
                    .orderStatus(ORDER_PLACED)
                    .orderAmount(product.getActualPrice() * o.getQuantity())
                    .product(product)
                    .user(user)
                    .build();

            // Now, you can save or process the orderDetail as needed
            orderDetailRepository.save(orderDetail);
        }
    }
}
