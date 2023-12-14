package com.hcmute.ecommerce.universeshop.customproduct;

import com.hcmute.ecommerce.universeshop.base.Authentication.JwtRequestFilter;
import com.hcmute.ecommerce.universeshop.base.exception.Constants;
import com.hcmute.ecommerce.universeshop.base.exception.ErrorMessage;
import com.hcmute.ecommerce.universeshop.base.exception.InputValidationException;
import com.hcmute.ecommerce.universeshop.base.exception.ResourceNotFoundException;
import com.hcmute.ecommerce.universeshop.base.utils.FirebaseUtils;
import com.hcmute.ecommerce.universeshop.image.ImageEntity;
import com.hcmute.ecommerce.universeshop.image.ImageRepository;
import com.hcmute.ecommerce.universeshop.product.ProductEntity;
import com.hcmute.ecommerce.universeshop.product.ProductRepository;
import com.hcmute.ecommerce.universeshop.product.ProductService;
import com.hcmute.ecommerce.universeshop.user.UserEntity;
import com.hcmute.ecommerce.universeshop.user.UserRepository;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomProductServiceImpl implements CustomProductService {

    private final CustomProductRepository customProductRepository;
    private final ProductRepository productRepository;
    private final ErrorMessage errorMessage;

    @Autowired
    private FirebaseUtils firebaseUtils;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    public CustomProductServiceImpl(CustomProductRepository customProductRepository, ProductRepository productRepository, ErrorMessage errorMessage) {
        this.customProductRepository = customProductRepository;
        this.productRepository = productRepository;
        this.errorMessage = errorMessage;
    }

    private void validateCustomProduct(CustomProductEntity customProductEntity) {
     if(customProductEntity.getProductEntity() == null) {
         throw new InputValidationException(errorMessage.getMessage(Constants.PRODUCT_NOT_FOUND));
     }
     if(customProductEntity.getUser() == null) {
         throw new InputValidationException(errorMessage.getMessage(Constants.USER_NOT_FOUND));
     }
     if(customProductEntity.getCustomImages() == null || customProductEntity.getCustomImages().size() <1) {
         throw new InputValidationException(errorMessage.getMessage(Constants.INVALID_INPUT_VALUE));
     }
    }

    @Override
    public CustomProductEntity saveCustomProduct(Long productId, List<MultipartFile> multipartFiles, String size) throws IOException {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(
                () -> new InputValidationException(errorMessage.getMessage(Constants.PRODUCT_NOT_FOUND))
        );
        String currentUser = JwtRequestFilter.CURRENT_USER;
        UserEntity user = userRepository.findById(currentUser).orElseThrow(() -> new ResourceNotFoundException(errorMessage.getMessage(Constants.USER_NOT_FOUND)));
        CustomProductEntity customProductEntity = new CustomProductEntity();
        customProductEntity.setProductEntity(productEntity);
        customProductEntity.setUser(user);
        customProductEntity.setSize(SizeEnum.valueOf(size));
        customProductEntity.setCustomImages(new ArrayList<>());
        for(MultipartFile file : multipartFiles) {
            ImageEntity imageEntity = new ImageEntity();
            String imageAddress = firebaseUtils.uploadImage(file, file.getName());
            imageEntity.setLink(imageAddress);
            imageRepository.save(imageEntity);
            customProductEntity.getCustomImages().add(imageEntity);
        }
       return customProductRepository.save(customProductEntity);
    }

    @Override
    public CustomProductEntity findCustomProductById(Long customProductId) {
        return customProductRepository.findById(customProductId).orElseThrow(
                () -> new ResourceNotFoundException(errorMessage.getMessage(Constants.PRODUCT_NOT_FOUND))
        );
    }

    @Override
    public List<CustomProductEntity> findCustomProductsByUser() {
        String username = JwtRequestFilter.CURRENT_USER;
        return customProductRepository.findByUser(username);
    }

    @Override
    public List<CustomProductEntity> findCustomProductsByProduct(Long productId) {
        return customProductRepository.findByProduct(productId);
    }
}
