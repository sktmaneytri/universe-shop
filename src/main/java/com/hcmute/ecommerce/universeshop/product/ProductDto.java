package com.hcmute.ecommerce.universeshop.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hcmute.ecommerce.universeshop.category.CategoryEntity;
import com.hcmute.ecommerce.universeshop.image.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    @NotNull
    @Size(min = 3, max = 30, message = "Product name size should be between 3-30")
    private String productName;
    @NotNull
    @DecimalMin(value = "0.00")
    private Double actualPrice;
    @NotNull
    @DecimalMin(value = "0.00")
    private Double discountedPrice;
    private String description;
    @NotNull
    private String manufacturer;
    @NotNull
    @Min(value = 0)
    private Integer quantity;
    private Set<ImageEntity> productImages;
    @JsonIgnoreProperties("products")
    private CategoryEntity category;
    private ProductStatus status;
}
