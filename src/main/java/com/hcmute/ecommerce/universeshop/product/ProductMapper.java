package com.hcmute.ecommerce.universeshop.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category.id", target = "category.id")
    ProductDto entityToDto(ProductEntity productEntity);

    @Mapping(source = "category.id", target = "category.id")
    ProductEntity dtoToEntity(ProductDto productDTO);

    List<ProductEntity> dtosToEntities(List<ProductDto> productDTOs);

    List<ProductDto> entitiesToDtos(List<ProductEntity> productEntities);
}
