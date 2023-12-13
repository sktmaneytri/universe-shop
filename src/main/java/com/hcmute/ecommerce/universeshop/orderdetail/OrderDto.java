package com.hcmute.ecommerce.universeshop.orderdetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private String orderCode;
    private String orderFullName;
    private String orderFullAddress;
    private String orderContactNumber;
    private String orderStatus;
    private Double orderAmount;

    private Boolean isPaid;
    private LocalDateTime createdDate;
    private List<OrderProductQuantity> orderItems;
}
