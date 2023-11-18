package com.hcmute.ecommerce.universeshop.product;

import com.hcmute.ecommerce.universeshop.Category.CategoryEntity;
import com.hcmute.ecommerce.universeshop.Category.CategoryRepository;
import com.hcmute.ecommerce.universeshop.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplementation implements ProductService {
    private ProductRepository productRepository;
    private UserService userService;
    private CategoryRepository categoryRepository;

    public ProductServiceImplementation(ProductRepository productRepository,
                                        UserService userService,
                                        CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductEntity createProduct(CreateProductRequest productRequest) {
        CategoryEntity topLevel = categoryRepository.findByName(
                productRequest.getToLavelCategory());
        if (topLevel == null) {
            CategoryEntity topLavelCategory = new CategoryEntity();
            topLavelCategory.setName(productRequest.getToLavelCategory());
            topLavelCategory.setLevel(1);
            topLevel = categoryRepository.save(topLavelCategory);
        }
        CategoryEntity secondLevel = categoryRepository.findByNameAndParant(
                productRequest.getSecondLavelCategory(), topLevel.getName());
        if (secondLevel == null) {
            CategoryEntity secondLavelCategory = new CategoryEntity();
            secondLavelCategory.setName(productRequest.getSecondLavelCategory());
            secondLavelCategory.setParentCategory(topLevel);
            secondLavelCategory.setLevel(2);
            secondLevel = categoryRepository.save(secondLavelCategory);
        }
        CategoryEntity thirdLevel = categoryRepository.findByNameAndParant(
                productRequest.getThirdLavelCategory(), secondLevel.getName());
        if (thirdLevel == null) {
            CategoryEntity thirdLavelCategory = new CategoryEntity();
            thirdLavelCategory.setName(productRequest.getThirdLavelCategory());
            thirdLavelCategory.setParentCategory(secondLevel);
            thirdLavelCategory.setLevel(3);
            thirdLevel = categoryRepository.save(thirdLavelCategory);
        }
        ProductEntity product = new ProductEntity();
        product.setTitle(productRequest.getTile());
        product.setColor(productRequest.getColor());
        product.setDescription(productRequest.getDescription());
        product.setDiscountPrice(productRequest.getDiscountedPrice());
        product.setDiscountPercent(productRequest.getDiscountPercent());
        product.setImageUrl(productRequest.getImageUrl());
        product.setBrand(productRequest.getBrand());
        product.setPrice(productRequest.getPrice());
        product.setSizes(productRequest.getSize());
        product.setQuantity(productRequest.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        ProductEntity savedProduct = productRepository.save(product);

        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        ProductEntity product = findProductById(productId);
        product.getSize().clear();
        productRepository.delete(product);
        return "Product deleted successfully";
    }

    @Override
    public ProductEntity updateProduct(Long productId, ProductEntity productRequest)
            throws ProductException {
        ProductEntity product = findProductById(productId);
        if (productRequest.getQuantity() != 0) {
            product.setQuantity(productRequest.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public ProductEntity findProductById(Long id) throws ProductException {
        Optional<ProductEntity> opt = productRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new ProductException("Product not found with id - " + id);
    }

    @Override
    public List<ProductEntity> findProductByCategory(String category) {
        return null;
    }

    @Override
    public Page<ProductEntity> getAllProduct(String category, List<String> colors,
                                             List<String> sizes, Integer minPrice,
                                             Integer maxPrice, Integer minDiscount,
                                             String sort, String stock, Integer pageNumber,
                                             Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<ProductEntity> products = productRepository.filterProducts(
                category, minPrice, maxPrice, minDiscount, sort);
        if (!colors.isEmpty()) {
            products = products.stream().filter(p -> colors.stream().anyMatch(
                    c -> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
        }
        if (stock != null) {
            if (stock.equals("in_stock")) {
                products = products.stream().filter(p -> p.getQuantity() > 0).collect(
                        Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products = products.stream().filter(p -> p.getQuantity() < 1).collect(
                        Collectors.toList());
            }
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
        List<ProductEntity> pageContent = products.subList(startIndex, endIndex);
        Page<ProductEntity> filteredProducts = new PageImpl<>(pageContent, pageable,
                products.size());
        return filteredProducts;
    }
}