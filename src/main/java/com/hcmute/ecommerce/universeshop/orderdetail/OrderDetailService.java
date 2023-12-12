package com.hcmute.ecommerce.universeshop.orderdetail;

import com.hcmute.ecommerce.universeshop.base.Authentication.JwtRequestFilter;
import com.hcmute.ecommerce.universeshop.base.exception.Constants;
import com.hcmute.ecommerce.universeshop.base.exception.ErrorMessage;
import com.hcmute.ecommerce.universeshop.base.exception.InputValidationException;
import com.hcmute.ecommerce.universeshop.base.exception.ResourceNotFoundException;
import com.hcmute.ecommerce.universeshop.base.utils.Generator;
import com.hcmute.ecommerce.universeshop.cart.CartEntity;
import com.hcmute.ecommerce.universeshop.customproduct.CustomProductEntity;
import com.hcmute.ecommerce.universeshop.customproduct.CustomProductRepository;
import com.hcmute.ecommerce.universeshop.orderinput.OrderInput;
import com.hcmute.ecommerce.universeshop.orderinput.OrderInputService;
import com.hcmute.ecommerce.universeshop.product.ProductDto;
import com.hcmute.ecommerce.universeshop.product.ProductEntity;
import com.hcmute.ecommerce.universeshop.product.ProductRepository;
import com.hcmute.ecommerce.universeshop.user.UserEntity;
import com.hcmute.ecommerce.universeshop.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.hcmute.ecommerce.universeshop.base.utils.Generator.generateRandomCode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class OrderDetailService {
    private static final String ORDER_PLACED = "PLACED";
    private static final String ORDER_SHIPPED = "SHIPPED";
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ErrorMessage errorMessage;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomProductRepository customProductRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderInputService orderInputService;

    public OrderDto placeOrder(OrderInput orderInput) {
        if(isNull(orderInput)) {
            throw new InputValidationException(errorMessage.getMessage(Constants.INVALID_INPUT_VALUE));
        }
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();
        Double orderAmountTotal = 0.0;
        for (OrderProductQuantity o : productQuantityList) {
            CustomProductEntity product = customProductRepository.findById(o.getCustomProductId()).orElseThrow(() -> new ResourceNotFoundException(errorMessage.getMessage(Constants.PRODUCT_NOT_FOUND)));
            String currentUser = JwtRequestFilter.CURRENT_USER;
            UserEntity user = userRepository.findById(currentUser).orElseThrow(() -> new ResourceNotFoundException(errorMessage.getMessage(Constants.USER_NOT_FOUND)));

            OrderDetail orderDetail = OrderDetail.builder()
                    .orderFullName(orderInput.getFullName())
                    .orderFullAddress(orderInput.getFullAddress())
                    .orderContactNumber(orderInput.getContactNumber())
                    .orderStatus(ORDER_PLACED)
                    .quantity(o.getQuantity())
                    .orderAmount(product.getProductEntity().getActualPrice() * o.getQuantity())
                    .customProductEntity(product)
                    .createdDate(LocalDateTime.now())
                    .user(user)
                    .build();
            orderAmountTotal += orderDetail.getOrderAmount();
            orderDetailRepository.save(orderDetail);
        }
        OrderDto orderDto = OrderDto.builder()
                .orderFullName(orderInput.getFullName())
                .orderFullAddress(orderInput.getFullAddress())
                .orderContactNumber(orderInput.getContactNumber())
                .orderItems(orderInput.getOrderProductQuantityList())
                .orderStatus(ORDER_PLACED)
                .createdDate(LocalDateTime.now())
                .orderCode(generateRandomCode())
                .orderAmount(orderAmountTotal)
                .build();
        return orderDto;
    }

    public OrderDto placeOrderFromCart() {
        String currentUser = JwtRequestFilter.CURRENT_USER;
        UserEntity user = userRepository.findById(currentUser).orElseThrow(() -> new ResourceNotFoundException(errorMessage.getMessage(Constants.USER_NOT_FOUND)));
        CartEntity cart = user.getCart();
        if(cart.getCartItem().isEmpty() || cart.getCartItem() == null) return null;
        OrderInput orderInput = orderInputService.createOrderInputFromCart(cart);
        return placeOrder(orderInput);
    }
    public List<OrderDetail> getAllOrders() {
        return orderDetailRepository.findAll();
    }
    public List<OrderDetail> getOrdersByStatus(String status) {
        return orderDetailRepository.findByStatus(status);
    }
    public OrderDetail findOrderDetailById(Long id) {
        return orderDetailRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(errorMessage.getMessage(Constants.ORDER_NOT_FOUND))
        );
    }
    public OrderDetail checkoutOrder(Long id) {
        OrderDetail orderCheckout = orderDetailRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(errorMessage.getMessage(Constants.ORDER_NOT_FOUND))
        );
        orderCheckout.setOrderStatus(ORDER_SHIPPED);
        return orderDetailRepository.save(orderCheckout);
    }
    public Statistic viewStatistics() {
        Statistic stat = new Statistic();
        stat.setTotalProduct(getTotalProductSold());
        stat.setTotalRevenue(getTotalRevenue());
        // Calculate revenue by month using Stream API
        stat.setRevenueByMonth(
                IntStream.rangeClosed(1, 12)
                        .boxed()
                        .collect(Collectors.toMap(
                                month -> getMonthName(month),
                                this::getRevenueByMonth
                        ))
        );

        // Calculate product sold by month using Stream API
        stat.setProductSoldByMonth(
                IntStream.rangeClosed(1, 12)
                        .boxed()
                        .collect(Collectors.toMap(
                                month -> getMonthName(month),
                                month -> getProductSoldByMonth(month).intValue()
                        ))
        );
        return stat;
    }
    public String getMonthName(int month) {
        switch (month) {
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: throw new InputValidationException(errorMessage.getMessage(Constants.INVALID_INPUT_VALUE));
        }
    }
    public double getRevenueByMonth(int month) {
        if(month > 0 && month < 13) {
            Double revenue = orderDetailRepository.getRevenueByMonth(month);
            System.out.println(revenue);
            if(revenue == null || revenue <= 0) revenue = 0.0;
            return revenue;
        }
        throw new InputValidationException(errorMessage.getMessage(Constants.INVALID_INPUT_VALUE));
    }
    public Integer getProductSoldByMonth(int month) {
        if(month > 0 && month < 13) {
            Integer products = orderDetailRepository.getProductSoldByMonth(month);
            if(products == null|| products <= 0) products = 0;
            return products;
        }
        throw new InputValidationException(errorMessage.getMessage(Constants.INVALID_INPUT_VALUE));
    }
    public Double getTotalRevenue() {
        Double totalRevenue = orderDetailRepository.getTotalRevenue();
        return totalRevenue;
    }
    public Integer getTotalProductSold() {
        Integer products = orderDetailRepository.getTotalProductSold();
        return products;
    }

    private Boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private Boolean isNull(OrderInput orderInput) {
        return isNullOrEmpty(orderInput.getFullName()) ||
                isNullOrEmpty(orderInput.getFullAddress()) ||
                isNullOrEmpty(orderInput.getContactNumber());
    }
}