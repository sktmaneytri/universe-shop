package com.hcmute.ecommerce.universeshop.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.ecommerce.universeshop.cart.Item.CartItemEntity;
import com.hcmute.ecommerce.universeshop.customer.CustomerEntity;
import com.hcmute.ecommerce.universeshop.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<CartItemEntity> cartItem;
    private Double totalPrices;
    private Integer totalItems;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private UserEntity user;

}
