package com.hcmute.ecommerce.universeshop.product;

import com.hcmute.ecommerce.universeshop.base.utils.FirebaseUtils;
import com.hcmute.ecommerce.universeshop.category.CategoryEntity;
import com.hcmute.ecommerce.universeshop.category.CategoryRepository;
import com.hcmute.ecommerce.universeshop.base.exception.Constants;
import com.hcmute.ecommerce.universeshop.base.exception.ErrorMessage;
import com.hcmute.ecommerce.universeshop.base.exception.InputValidationException;
import com.hcmute.ecommerce.universeshop.base.exception.ResourceNotFoundException;
import com.hcmute.ecommerce.universeshop.image.ImageEntity;
import com.hcmute.ecommerce.universeshop.image.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ErrorMessage errorMessage;
    private final CategoryRepository categoryRepository;
    private final FirebaseUtils firebaseUtils;
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, ErrorMessage errorMessage, CategoryRepository categoryRepository, FirebaseUtils firebaseUtils) {
        this.productRepository = productRepository;
        this.productMapper  = productMapper;
        this.errorMessage = errorMessage;
        this.categoryRepository = categoryRepository;
        this.firebaseUtils = firebaseUtils;
    }

    @Override
    public void initProducts() {
        ImageEntity frontBlueTShirt = ImageEntity.builder()
                .link("https://media2.coolmate.me/cdn-cgi/image/quality=80,format=auto/uploads/November2022/recycle-tee-nhan-ep-navy_41.jpg")
                .build();
        ImageEntity backBlueTShirt = ImageEntity.builder()
                .link("https://media2.coolmate.me/cdn-cgi/image/quality=80,format=auto/uploads/April2022/canvainavy_4.jpg")
                .build();
        ImageEntity frontBlackTShirt = ImageEntity.builder()
                .link("https://media2.coolmate.me/cdn-cgi/image/quality=100/uploads/November2022/recycle-tee-nhan-ep-den_39.jpg")
                .build();
        ImageEntity backBlackTShirt = ImageEntity.builder()
                .link("https://media2.coolmate.me/cdn-cgi/image/quality=100/uploads/April2022/canvaiden_4.jpg")
                .build();
        ImageEntity frontGreyTShirt = ImageEntity.builder()
                .link("https://media2.coolmate.me/cdn-cgi/image/quality=100/uploads/September2023/AT220.XAM2.jpg")
                .build();
        ImageEntity backGreyTShirt = ImageEntity.builder()
                .link("https://www.coolmate.me/product/ao-thun-100-cotton-220gsm?color=xam-melange#")
                .build();
        Set<ImageEntity> blue = Set.of(frontBlueTShirt, backBlueTShirt);
        Set<ImageEntity> black = Set.of(frontBlackTShirt, backBlackTShirt);
        Set<ImageEntity> grey = Set.of(frontGreyTShirt, backGreyTShirt);
        imageRepository.saveAll(blue);
        imageRepository.saveAll(black);
        imageRepository.saveAll(grey);

        ProductEntity product1 = ProductEntity.builder()
                .productName("UNIVERSE Spooky Blue Tee")
                .description("ÁO UNIVERSE TEE ĐƯỢC LÀM TỪ COTTON 100% CHỐNG NHĂN CHỐNG PHAI MÀU, ĐẢM BẢO SỨC KHỎE NGƯỜI DÙNG")
                .actualPrice(Double.valueOf(200000))
                .discountedPrice(Double.valueOf(0))
                .quantity(1000)
                .manufacturer("UNIVERSE FACTORY")
                .productImages(blue)
                .category(categoryRepository.findById(1L).get())
                .status(ProductStatus.AVAILABLE)
                .build();
        ProductEntity product2 = ProductEntity.builder()
                .productName("UNIVERSE Book Boxy Black Tee")
                .description("ÁO UNIVERSE TEE ĐƯỢC LÀM TỪ COTTON 100% CHỐNG NHĂN CHỐNG PHAI MÀU, ĐẢM BẢO SỨC KHỎE NGƯỜI DÙNG")
                .actualPrice(Double.valueOf(400000))
                .discountedPrice(Double.valueOf(0))
                .quantity(1000)
                .manufacturer("UNIVERSE FACTORY")
                .productImages( black)
                .category(categoryRepository.findById(1L).get())
                .status(ProductStatus.AVAILABLE)
                .build();
        ProductEntity product3 = ProductEntity.builder()
                .productName("UNIVERSE Season Grey 3 Tee")
                .description("ÁO UNIVERSE TEE ĐƯỢC LÀM TỪ COTTON 100% CHỐNG NHĂN CHỐNG PHAI MÀU, ĐẢM BẢO SỨC KHỎE NGƯỜI DÙNG")
                .actualPrice(Double.valueOf(600000))
                .discountedPrice(Double.valueOf(0))
                .productImages(grey)
                .quantity(1000)
                .manufacturer("UNIVERSE FACTORY")
                .category(categoryRepository.findById(1L).get())
                .status(ProductStatus.AVAILABLE)
                .build();
        ProductEntity product4 = ProductEntity.builder()
                .productName("UNIVERSE ChillTimes Tee")
                .description("ÁO UNIVERSE TEE ĐƯỢC LÀM TỪ COTTON 100% CHỐNG NHĂN CHỐNG PHAI MÀU, ĐẢM BẢO SỨC KHỎE NGƯỜI DÙNG")
                .actualPrice(Double.valueOf(100000))
                .discountedPrice(Double.valueOf(0))
                .productImages(black)
                .quantity(1000)
                .manufacturer("UNIVERSE FACTORY")
                .category(categoryRepository.findById(1L).get())
                .status(ProductStatus.AVAILABLE)
                .build();
        ProductEntity product7 = ProductEntity.builder()
                .productName("UNIVERSE Jadon Jackets")
                .description("ÁO UNIVERSE TEE ĐƯỢC LÀM TỪ COTTON 100% CHỐNG NHĂN CHỐNG PHAI MÀU, ĐẢM BẢO SỨC KHỎE NGƯỜI DÙNG")
                .actualPrice(Double.valueOf(250000))
                .discountedPrice(Double.valueOf(0))
                .productImages(grey)
                .quantity(1000)
                .manufacturer("UNIVERSE FACTORY")
                .category(categoryRepository.findById(3L).get())
                .status(ProductStatus.AVAILABLE)
                .build();
        ProductEntity product5 = ProductEntity.builder()
                .productName("UNIVERSE HHow Jackets")
                .description("ÁO UNIVERSE TEE ĐƯỢC LÀM TỪ COTTON 100% CHỐNG NHĂN CHỐNG PHAI MÀU, ĐẢM BẢO SỨC KHỎE NGƯỜI DÙNG")
                .actualPrice(Double.valueOf(203000))
                .discountedPrice(Double.valueOf(0))
                .productImages(blue)
                .quantity(1000)
                .manufacturer("UNIVERSE FACTORY")
                .category(categoryRepository.findById(3L).get())
                .status(ProductStatus.AVAILABLE)
                .build();
        ProductEntity product6 = ProductEntity.builder()
                .productName("UNIVERSE Yasser Hoodie")
                .description("ÁO UNIVERSE TEE ĐƯỢC LÀM TỪ COTTON 100% CHỐNG NHĂN CHỐNG PHAI MÀU, ĐẢM BẢO SỨC KHỎE NGƯỜI DÙNG")
                .actualPrice(Double.valueOf(230000))
                .discountedPrice(Double.valueOf(0))
                .productImages(black)
                .quantity(1000)
                .manufacturer("UNIVERSE FACTORY")
                .category(categoryRepository.findById(5L).get())
                .status(ProductStatus.AVAILABLE)
                .build();
        ProductEntity product8 = ProductEntity.builder()
                .productName("UNIVERSE Jadon Hoodie")
                .description("ÁO UNIVERSE TEE ĐƯỢC LÀM TỪ COTTON 100% CHỐNG NHĂN CHỐNG PHAI MÀU, ĐẢM BẢO SỨC KHỎE NGƯỜI DÙNG")
                .actualPrice(Double.valueOf(400000))
                .productImages(black)
                .discountedPrice(Double.valueOf(0))
                .quantity(1000)
                .manufacturer("UNIVERSE FACTORY")
                .category(categoryRepository.findById(5L).get())
                .status(ProductStatus.AVAILABLE)
                .build();
        ProductEntity product9 = ProductEntity.builder()
                .productName("UNIVERSE Jadon SoMi")
                .description("ÁO UNIVERSE TEE ĐƯỢC LÀM TỪ COTTON 100% CHỐNG NHĂN CHỐNG PHAI MÀU, ĐẢM BẢO SỨC KHỎE NGƯỜI DÙNG")
                .actualPrice(Double.valueOf(240000))
                .discountedPrice(Double.valueOf(0))
                .productImages(grey)
                .quantity(1000)
                .manufacturer("UNIVERSE FACTORY")
                .category(categoryRepository.findById(2L).get())
                .status(ProductStatus.AVAILABLE)
                .build();
        ProductEntity product10 = ProductEntity.builder()
                .productName("UNIVERSE Msson Somi")
                .description("ÁO UNIVERSE TEE ĐƯỢC LÀM TỪ COTTON 100% CHỐNG NHĂN CHỐNG PHAI MÀU, ĐẢM BẢO SỨC KHỎE NGƯỜI DÙNG")
                .actualPrice(Double.valueOf(202000))
                .productImages(grey)
                .discountedPrice(Double.valueOf(0))
                .quantity(1000)
                .manufacturer("UNIVERSE FACTORY")
                .category(categoryRepository.findById(2L).get())
                .status(ProductStatus.AVAILABLE)
                .build();

        List<ProductEntity> productEntities = Arrays.asList(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10);
        productRepository.saveAll(productEntities);
    }

    @Override
    public List<ProductDto> getAllProducts(Pageable pageable, String searchKey) {
        if(searchKey.equals("")) {
            List<ProductEntity> productEntities = productRepository.findAll();
            List<ProductDto> productDtoList = productMapper.entitiesToDtos(productEntities);
            if(productDtoList.isEmpty()) {
                return productDtoList;
            }
            return sortProduct(productDtoList, Comparator.comparing(ProductDto::getId));
        } else {
            List<ProductEntity> productEntities = productRepository.findProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
                    searchKey, searchKey, pageable
            );
            List<ProductDto> productDtoList = productMapper.entitiesToDtos(productEntities);
            if(productDtoList.isEmpty()) {
                return productDtoList;
            }
            return sortProduct(productDtoList, Comparator.comparing(ProductDto::getId));
        }
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
    public ProductDto setImageProduct(List<MultipartFile> files, Long productId) throws IOException {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException(errorMessage.getMessage(Constants.PRODUCT_NOT_FOUND))
        );
        for(MultipartFile file : files) {
            ImageEntity imageEntity = new ImageEntity();
            String imageAddress = firebaseUtils.uploadImage(file, file.getName());
            imageEntity.setLink(imageAddress);
            imageRepository.save(imageEntity);
            productEntity.getProductImages().add(imageEntity);
            productRepository.save(productEntity);
        }
        return productMapper.entityToDto(productEntity);
    }

    @Override
    public ProductDto updateProduct(Long productId,ProductEntity productEntity) {
       ProductEntity productEntityUpdated = productRepository.findById(productId)
               .orElseThrow(() -> new InputValidationException(errorMessage.getMessage(Constants.PRODUCT_NOT_FOUND)));
       if(!productEntity.equals(productEntityUpdated) && isExistedProductName(productEntity.getProductName())) {
           throw new InputValidationException(errorMessage.getMessage(Constants.PRODUCT_NAME_EXISTED));
       }
       productEntityUpdated.setProductName(productEntity.getProductName());
       productEntityUpdated.setCategory(productEntity.getCategory());
       productEntityUpdated.setActualPrice(productEntity.getActualPrice());
       productEntityUpdated.setDiscountedPrice(productEntity.getActualPrice());
       productEntityUpdated.setDescription(productEntity.getDescription());
       productEntityUpdated.setManufacturer(productEntity.getManufacturer());
       productEntityUpdated.setQuantity(productEntity.getQuantity());

       return productMapper.entityToDto(productRepository.save(productEntityUpdated));
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
                productEntity.getActualPrice() < 0 ||
                productEntity.getDiscountedPrice() < 0 ||
                productEntity.getQuantity() < 0;
    }
    private Boolean isNull(ProductDto productEntity) {
        return isNullOrEmpty(productEntity.getProductName()) ||
                isNullOrEmpty(productEntity.getDescription()) ||
                productEntity.getActualPrice() < 0 ||
                productEntity.getDiscountedPrice() < 0 ||
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

    public List<ProductDto> sortProduct(List<ProductDto> productDtoList, Comparator<ProductDto> comparator) {
        List<ProductDto> sortedProduct = new ArrayList<>(productDtoList);
        sortedProduct.sort(comparator);
        return sortedProduct;
    }
}
