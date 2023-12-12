package com.hcmute.ecommerce.universeshop.customproduct;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CustomProductService {

    CustomProductEntity saveCustomProduct(Long productId, List<MultipartFile> multipartFiles) throws IOException;

    CustomProductEntity findCustomProductById(Long customProductId);

    List<CustomProductEntity> findCustomProductsByUser();

    List<CustomProductEntity> findCustomProductsByProduct(Long productId);
}
