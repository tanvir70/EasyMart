package com.tanvir.eshoppers.repository;

import com.tanvir.eshoppers.dto.ProductDTO;

import java.util.List;

public interface ProductRepository {
    List<ProductDTO> findAllProducts();
}
