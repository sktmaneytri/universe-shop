package com.hcmute.ecommerce.universeshop.orderdetail;

import com.hcmute.ecommerce.universeshop.orderinput.OrderInput;
import com.hcmute.ecommerce.universeshop.product.ProductService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class OrderDetailResource {
    @Autowired
    private OrderDetailService orderDetailService;
    @PostMapping({"/placeOrder"})
    @PreAuthorize("hasRole('USER')")
    public void placeOrder(@RequestBody OrderInput orderInput) {
        orderDetailService.placeOrder(orderInput);
    }

    @PostMapping({"/carts/order"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderDto> placeOrderFromCart() {
       OrderDto orderPlaced = orderDetailService.placeOrderFromCart();
       return ResponseEntity.ok(orderPlaced);
    }
    @GetMapping({"/orders"})
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<List<OrderDetail>> viewAllOrderDetails() {
        List<OrderDetail> orderDetailEntities = orderDetailService.getAllOrders();
        return ResponseEntity.ok(orderDetailEntities);
    }
    @GetMapping({"/orders/{id}"})
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable Long id) {
       OrderDetail orderDetail = orderDetailService.findOrderDetailById(id);
        return ResponseEntity.ok(orderDetail);
    }
    @GetMapping({"/orders/status/"})
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<List<OrderDetail>> viewOrderDetailsByStatus(@RequestParam("status") String status) {
        List<OrderDetail> orderDetailEntities = orderDetailService.getOrdersByStatus(status);
        return ResponseEntity.ok(orderDetailEntities);
    }
    @GetMapping({"/orders/statistic"})
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Statistic> viewOrderDetailsByStatus() {
        return ResponseEntity.ok(orderDetailService.viewStatistics());
    }
    @PostMapping({"/orders/{id}/shipped"})
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<OrderDetail> shippedOrder(@PathVariable Long id) {
        OrderDetail orderShipped = orderDetailService.checkoutOrder(id);
        return ResponseEntity.ok(orderShipped);
    }
}
