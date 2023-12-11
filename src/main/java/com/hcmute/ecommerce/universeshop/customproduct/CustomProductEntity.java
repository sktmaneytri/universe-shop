package com.hcmute.ecommerce.universeshop.customproduct;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hcmute.ecommerce.universeshop.category.CategoryEntity;
import com.hcmute.ecommerce.universeshop.image.ImageEntity;
import com.hcmute.ecommerce.universeshop.product.ProductEntity;
import com.hcmute.ecommerce.universeshop.product.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity(name = "custom_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 3, max = 30, message = "Product name size should be between 3-30")
    @Column(name = "product_name")
    private String productName;
    @NotNull
    @DecimalMin(value = "0.00")
    @Column(name = "actual_price")
    private Double actualPrice;
    @NotNull
    @DecimalMin(value = "0.00")
    @Column(name = "discounted_price")
    private Double discountedPrice;
    @OneToMany
    private List<ImageEntity> productImages;

    @ManyToOne
    private ProductEntity productEntity;
}
