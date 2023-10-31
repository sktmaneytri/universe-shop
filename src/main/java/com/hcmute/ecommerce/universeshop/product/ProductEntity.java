package com.hcmute.ecommerce.universeshop.product;

import com.hcmute.ecommerce.universeshop.Category.CategoryEntity;
import com.hcmute.ecommerce.universeshop.review.RatingEntity;
import com.hcmute.ecommerce.universeshop.review.ReviewEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "discounted_price")
    private int discountPrice;

    @Column(name = "discount_percent")
    private int discountPercent;

    @Column(name = "quantity")
    private int quantity;
    @Column(name = "brand")
    private String brand;

    private String color;
//    @Embedded
//    @ElementCollection
//    @Column(name = "sizes")
//    private Set<SizeEntity> sizes = new HashSet<>();

    @Column(name = "images_url")
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RatingEntity> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ReviewEntity> reviews = new ArrayList<>();

    @Column(name = "num_ratings")
    private int numRatings;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    private LocalDateTime createdAt;


}
