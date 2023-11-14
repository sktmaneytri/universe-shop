package com.hcmute.ecommerce.universeshop.seller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.ecommerce.universeshop.product.ProductEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity(name = "seller")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer sellerId;

    @NotNull(message="Please enter the first name")
    @Pattern(regexp="[A-Za-z\\s]+", message="First Name should contains alphabets only")
    private String firstName;

    @NotNull(message="nPlease enter the last name")
    @Pattern(regexp="[A-Za-z\\s]+", message="First Name should contains alphabets only")
    private String lastName;

    @Pattern(regexp="[A-Za-z0-9!@#$%^&*_]{8,15}", message="Please Enter a valid Password")
    private String password;

    @NotNull(message="Please enter your mobile Number")
    @Pattern(regexp="[6789]{1}[0-9]{9}", message="Enter a valid Mobile Number")
    @Column(unique = true)
    private String mobile;

    @Email
    @Column(unique = true)
    private String emailId;
}
