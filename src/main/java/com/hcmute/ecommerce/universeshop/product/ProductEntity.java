package com.hcmute.ecommerce.universeshop.product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hcmute.ecommerce.universeshop.category.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 3, max = 30, message = "Product name size should be between 3-30")
    private String productName;
    @NotNull
    @DecimalMin(value = "0.00")
    @Column(name = "actual_price")
    private Double actualPrice;

    @NotNull
    @DecimalMin(value = "0.00")
    @Column(name = "discounted_price")
    private Double discountedPrice;
    @NotNull
    private String description;
    @NotNull
    private String manufacturer;
    @NotNull
    @Min(value = 0)
    private Integer quantity;
    private String image;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties({"products"})
    private CategoryEntity category;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
}
