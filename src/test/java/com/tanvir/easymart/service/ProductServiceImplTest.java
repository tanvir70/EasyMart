package com.tanvir.easymart.service;

import com.tanvir.easymart.domain.Product;
import com.tanvir.easymart.dto.ProductDTO;
import com.tanvir.easymart.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {
    private static final Product APPLE_I_PAD = new Product(
            1L,
            "Apple iPad",
            "Apple iPad 10.2 32GB",
            BigDecimal.valueOf(369.99));
    private static final Product HEADPHONE
            = new Product(
            2L,
            "Headphones",
            "Jabra Elite Bluetooth Headphones",
            BigDecimal.valueOf(249.99)
    );

    private ProductRepository productRepository;
    private ProductService productService;
    @Before
    public void setUp() throws Exception{
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }
    @Test
    public void testFindAllProductSortedByName(){
        when(productRepository.findAllProducts()).thenReturn(List.of(HEADPHONE,APPLE_I_PAD));
        List<ProductDTO> sortedByName = productService.findAllProductSortedByName();
        Assert.assertEquals(APPLE_I_PAD.getName(),sortedByName.get(0).getName());
        Assert.assertEquals(HEADPHONE.getName(),sortedByName.get(1).getName());
    }

}