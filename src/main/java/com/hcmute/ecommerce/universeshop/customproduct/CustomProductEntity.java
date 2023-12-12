package com.hcmute.ecommerce.universeshop.customproduct;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hcmute.ecommerce.universeshop.category.CategoryEntity;
import com.hcmute.ecommerce.universeshop.image.ImageEntity;
import com.hcmute.ecommerce.universeshop.product.ProductEntity;
import com.hcmute.ecommerce.universeshop.product.ProductStatus;
import com.hcmute.ecommerce.universeshop.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class CustomProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonIgnore
    private UserEntity user;
    @ManyToOne
    private ProductEntity productEntity;
    @Column(name = "custom_images")
    @OneToMany
    private List<ImageEntity> customImages;
}
