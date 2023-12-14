package com.hcmute.ecommerce.universeshop.cart;

import com.hcmute.ecommerce.universeshop.customproduct.SizeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartRequest {
    private Long customProductId;
    private String size;
    private Integer quantity;
}
