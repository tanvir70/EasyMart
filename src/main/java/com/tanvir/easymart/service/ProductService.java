package com.tanvir.easymart.service;

import com.tanvir.easymart.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllProductSortedByName();
}
