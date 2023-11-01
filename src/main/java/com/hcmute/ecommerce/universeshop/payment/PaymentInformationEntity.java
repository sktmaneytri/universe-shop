package com.hcmute.ecommerce.universeshop.payment;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Embeddable
@Table(name = "payment_information")
public class PaymentInformationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cardholder_name")
    private String cardholderName;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "expires_date")
    private LocalDate expirationDate;
    private String cvv;
}
