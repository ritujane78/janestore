package com.jane.janestore.service;



import com.jane.janestore.dto.ProductDto;

import java.util.List;

public interface IProductService {

    List<ProductDto> getProducts();
}
