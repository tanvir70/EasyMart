package com.tanvir.easymart.repository;

import com.tanvir.easymart.domain.Product;
import com.tanvir.easymart.dto.ProductDTO;

import java.util.List;

public interface ProductRepository {
    List<Product> findAllProducts();
}
