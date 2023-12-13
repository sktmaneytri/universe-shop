    package com.hcmute.ecommerce.universeshop.orderdetail;

    import com.hcmute.ecommerce.universeshop.customproduct.CustomProductEntity;
    import com.hcmute.ecommerce.universeshop.product.ProductEntity;
    import com.hcmute.ecommerce.universeshop.user.UserEntity;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import javax.persistence.*;
    import java.time.LocalDateTime;

    @Entity(name = "order_detail")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class OrderDetail {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String orderFullName;
        private String orderFullAddress;
        private String orderContactNumber;
        private String orderStatus;
        private Boolean isPaid;
        private Integer quantity;
        private Double orderAmount;
        private LocalDateTime createdDate;
        @OneToOne
        private CustomProductEntity customProductEntity;
        @OneToOne
        private UserEntity user;
    }
