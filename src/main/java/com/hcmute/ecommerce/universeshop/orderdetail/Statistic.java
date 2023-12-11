package com.hcmute.ecommerce.universeshop.orderdetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Statistic {
    private Double totalRevenue;
    private Integer totalProduct;
    private Map<String, Double> revenueByMonth = new HashMap<>();
    private Map<String, Integer> productSoldByMonth = new HashMap<>();
}
