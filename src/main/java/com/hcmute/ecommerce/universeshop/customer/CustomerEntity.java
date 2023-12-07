//package com.hcmute.ecommerce.universeshop.customer;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.hcmute.ecommerce.universeshop.cart.CartEntity;
//import com.hcmute.ecommerce.universeshop.customer.address.AddressEntity;
//import com.hcmute.ecommerce.universeshop.order.OrderEntity;
//import com.hcmute.ecommerce.universeshop.payment.CreditCard;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//import javax.persistence.*;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//@Entity(name = "customer")
//public class CustomerEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotNull(message = "First Name cannot be NULL")
//    @Pattern(regexp = "[A-Za-z.\\s]+", message = "Enter valid characters in first name")
//    private String firstName;
//
//    @NotNull(message = "Last Name cannot be NULL")
//    @Pattern(regexp = "[A-Za-z.\\s]+", message = "Enter valid characters in last name")
//    private String lastName;
//
//    @NotNull(message = "Please enter the mobile Number")
//    @Column(unique = true)
//    @Pattern(regexp = "[6789]{1}[0-9]{9}", message = "Enter valid 10 digit mobile number")
//    private String mobileNo;
//
//
//    @NotNull(message = "Please enter the emaild id")
//    @Column(unique = true)
//    @Email
//    private String emailId;
//
//    @NotNull(message = "Please enter the password")
//    @Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Password must be 8-15 characters in length and can include A-Z, a-z, 0-9, or special characters !@#$%^&*_")
//    private String password;
//
//
//    private LocalDateTime createdOn;
//
//    @Embedded
//    private CreditCard creditCard;
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn()
//    private List<AddressEntity> addresses;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.LAZY)
//    @JsonIgnoreProperties("exercise")
//    private List<OrderEntity> orders;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    private CartEntity customerCart;
//}
