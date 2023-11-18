package com.hcmute.ecommerce.universeshop.cart.Item;

import com.hcmute.ecommerce.universeshop.cart.CartEntity;
import com.hcmute.ecommerce.universeshop.product.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Data
@Entity(name = "cart_item")
@NoArgsConstructor
@AllArgsConstructor
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;
    @Min(value = 0)
    private int quantity;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
