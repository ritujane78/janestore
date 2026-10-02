package com.jane.janestore.service.impl;


import com.jane.janestore.dto.ProductDto;
import com.jane.janestore.entity.Product;
import com.jane.janestore.repository.ProductRepository;
import com.jane.janestore.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> getProducts() {
        return productRepository.findAll()
                .stream().map(this::transformToDTO).collect(Collectors.toList());
    }

    private ProductDto transformToDTO(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        productDto.setProductId(product.getId());
        return productDto;
    }
}
