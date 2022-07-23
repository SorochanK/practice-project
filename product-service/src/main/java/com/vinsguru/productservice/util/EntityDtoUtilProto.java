package com.vinsguru.productservice.util;

import com.vinsguru.productservice.grpc.dto.ProductDto;
import com.vinsguru.productservice.entity.Product;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtilProto {

    public static ProductDto toDto(Product product){
        ProductDto dto = ProductDto.newBuilder().build();
        BeanUtils.copyProperties(product, dto);
        return dto;
    }

    public static Product toEntity(ProductDto productDto){
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }

}
