package com.hcmute.ecommerce.universeshop.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.ecommerce.universeshop.cart.Item.CartItemEntity;
import com.hcmute.ecommerce.universeshop.customer.CustomerEntity;
import com.hcmute.ecommerce.universeshop.customer.address.AddressEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name="orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @PastOrPresent
    private LocalDate date;
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;

    private Double total;

    private String cardNumber;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

//    @OneToMany
//    private List<CartItemEntity> ordercartItems = new ArrayList<>();
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "address_id")
    private AddressEntity address;
}
