package com.hcmute.ecommerce.universeshop.orderdetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("SELECT o FROM order_detail o WHERE o.orderStatus = :status")
    List<OrderDetail> findByStatus(@Param("status") String status);

    @Query("SELECT SUM(o.orderAmount) FROM order_detail o")
    Double getTotalRevenue ();
    @Query("SELECT SUM(o.quantity) FROM order_detail o")
    Integer getTotalProductSold();
    @Query("SELECT SUM(o.orderAmount) FROM order_detail o WHERE MONTH(o.createdDate) = :month")
    Double getRevenueByMonth(@Param("month") int month);
    @Query("SELECT SUM(o.quantity) FROM order_detail o WHERE MONTH(o.createdDate) = :month")
    Integer getProductSoldByMonth(@Param("month") int month);
}
