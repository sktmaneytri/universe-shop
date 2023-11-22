package com.hcmute.ecommerce.universeshop.orderinput;

import com.hcmute.ecommerce.universeshop.orderdetail.OrderProductQuantity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInput {
    private String fullName;
    private String fullAddress;
    private String contactNumber;
    private String alternateContactNumber;

    private List<OrderProductQuantity> orderProductQuantityList;
}
