package com.hcmute.ecommerce.universeshop.product;

import com.hcmute.ecommerce.universeshop.Category.CategoryEntity;
import com.hcmute.ecommerce.universeshop.Category.CategoryRepository;
import com.hcmute.ecommerce.universeshop.base.exception.Constants;
import com.hcmute.ecommerce.universeshop.base.exception.ErrorMessage;
import com.hcmute.ecommerce.universeshop.base.exception.InputValidationException;
import com.hcmute.ecommerce.universeshop.base.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ErrorMessage errorMessage;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, ErrorMessage errorMessage, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productMapper  = productMapper;
        this.errorMessage = errorMessage;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productMapper.entitiesToDtos(productRepository.findAll());
    }

    @Override
    public ProductDto getProductById(Long productId) {
        return productMapper.entityToDto(
                productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException(errorMessage.getMessage(Constants.PRODUCT_NOT_FOUND)))
        );
    }
    @Override
    public ProductDto getProductByName(String productName) {
        return productMapper.entityToDto(
                productRepository.findByProductName(productName)
                        .orElseThrow(() -> new ResourceNotFoundException(errorMessage.getMessage(Constants.PRODUCT_NOT_FOUND)))
        );
    }
    @Override
    public List<ProductDto> getProductsByManufacturer(String manufacturer) {
        return productMapper.entitiesToDtos(productRepository.findByManufacturer(manufacturer));
    }

    @Override
    public List<ProductDto> getSoldOutProducts() {
        return productMapper.entitiesToDtos(productRepository.findProductsByQuantityBelowThreshold(1));
    }

    @Override
    public List<ProductDto> getProductsByCategoryName(String categoryName) {
        return productMapper.entitiesToDtos(productRepository.findByCategory_Name(categoryName));
    }

    @Override
    public List<ProductDto> getProductsByQuantityBelowThreshold(int quantityThreshold) {
        return productMapper.entitiesToDtos(productRepository.findProductsByQuantityBelowThreshold(10));
    }

    @Override
    public ProductEntity createProduct(ProductEntity productEntity) {
        if (isNull(productEntity)) {
            throw new InputValidationException(errorMessage.getMessage(Constants.CONTENT_MUST_NOT_BE_NULL));
        }

        if (isExistedProductName(productEntity.getProductName())) {
            throw new InputValidationException(errorMessage.getMessage(Constants.PRODUCT_NAME_EXISTED));
        }

        // Assuming productDto has categoryId field for the associated category
        Long categoryId = productEntity.getCategory().getId();
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage.getMessage(Constants.CATEGORY_NOT_FOUND)));

        // Set the category in the productDto
        productEntity.setCategory(categoryEntity);
        // Set the status (assuming you have a default status)
        productEntity.setStatus(ProductStatus.AVAILABLE);

        // Map the DTO to the entity and save it
        return productRepository.save(productEntity);
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        return null;
    }

    @Override
    public void deleteProductById(Long productId) {
        if(isExistedProductId(productId)) {
            productRepository.deleteById(productId);
        } else {
            throw new InputValidationException(errorMessage.getMessage(Constants.PRODUCT_NOT_FOUND));
        }
    }

    private Boolean isNull(ProductEntity productEntity) {
        return isNullOrEmpty(productEntity.getProductName()) ||
                isNullOrEmpty(productEntity.getDescription()) ||
                productEntity.getPrice() < 0 ||
                productEntity.getQuantity() < 0;
    }
    private Boolean isNull(ProductDto productEntity) {
        return isNullOrEmpty(productEntity.getProductName()) ||
                isNullOrEmpty(productEntity.getDescription()) ||
                productEntity.getPrice() < 0 ||
                productEntity.getQuantity() < 0;
    }
    private Boolean isExistedProductName(String productName) {
        return productRepository.isExistsProductName(productName);
    }
    private Boolean isExistedProductId(Long productId) {
        return productRepository.existsById(productId);
    }
    private Boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
