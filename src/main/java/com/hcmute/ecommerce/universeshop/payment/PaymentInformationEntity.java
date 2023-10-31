package com.hcmute.ecommerce.universeshop.payment;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
public class PaymentInformationEntity {
    @Column(name = "cardholder_name")
    private String cardholderName;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "expires_date")
    private LocalDate expirationDate;
    private String cvv;
}
