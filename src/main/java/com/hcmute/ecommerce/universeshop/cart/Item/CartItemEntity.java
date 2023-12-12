package com.hcmute.ecommerce.universeshop.cart.Item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.ecommerce.universeshop.cart.CartEntity;
import com.hcmute.ecommerce.universeshop.customproduct.CustomProductEntity;
import com.hcmute.ecommerce.universeshop.product.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

@Data
@Entity(name = "cart_item")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(value = 1)
    private int quantity;
    private double totalPrice;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private CartEntity cart;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemEntity that = (CartItemEntity) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "custom_product_id")
    private CustomProductEntity customProduct;
}
