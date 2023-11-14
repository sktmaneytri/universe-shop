package com.hcmute.ecommerce.universeshop.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.ecommerce.universeshop.cart.Item.CartItemEntity;
import com.hcmute.ecommerce.universeshop.customer.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItemEntity> cartItems = new ArrayList<>();

    private Double cartTotal;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private CustomerEntity customer;

}
