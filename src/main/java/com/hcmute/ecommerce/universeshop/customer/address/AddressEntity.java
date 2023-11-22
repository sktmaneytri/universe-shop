package com.hcmute.ecommerce.universeshop.customer.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.ecommerce.universeshop.customer.CustomerEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@Entity(name = "address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "[A-Za-z0-9\\s-]{3,}", message = "Not a valid street no")
    private String streetNo;

    @Pattern(regexp = "[A-Za-z0-9\\s-]{3,}", message = "Not a valid building name")
    private String buildingName;

    @NotNull
    @Pattern(regexp = "[A-Za-z0-9\\s-]{3,}", message = "Not a valid locality name")
    private String locality;

    @NotNull(message = "City name cannot be null")
    @Pattern(regexp = "[A-Za-z\\s]{2,}", message = "Not a valid city name")
    private String city;

    @NotNull(message = "State name cannot be null")
    private String state;

    @NotNull(message = "Pincode cannot be null")
    @Pattern(regexp = "[0-9]{6}", message = "Pincode not valid. Must be 6 digits")
    private String pinCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private CustomerEntity customer;
}