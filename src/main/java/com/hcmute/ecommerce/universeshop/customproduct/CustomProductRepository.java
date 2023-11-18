package com.hcmute.ecommerce.universeshop.customproduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomProductRepository extends JpaRepository<CustomProductEntity, Long> {
}
