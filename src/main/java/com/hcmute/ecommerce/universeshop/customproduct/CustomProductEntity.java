package com.hcmute.ecommerce.universeshop.customproduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "custom_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomProductEntity {
    @Id
    private Long id;
}
