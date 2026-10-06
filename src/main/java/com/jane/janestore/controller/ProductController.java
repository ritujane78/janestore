package com.jane.janestore.controller;

import com.jane.janestore.dto.ProductDto;
import com.jane.janestore.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
// @CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final IProductService iProductService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() { // DTO Pattern
        List<ProductDto> productList = iProductService.getProducts();
        return ResponseEntity.status(200).body(productList);
    }

}
