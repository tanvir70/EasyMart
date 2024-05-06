package com.tanvir.easymart.repository;

import com.tanvir.easymart.domain.Product;
import com.tanvir.easymart.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.List;

public class DummyProductRepositoryImpl implements ProductRepository {

	public static final List<Product> ALL_PRODUCTS() {

		return List.of(
						new Product(
										1L,
										"Apple iPad",
										"Apple iPad 10.2 32GB",
										BigDecimal.valueOf(369.99)
						),
						new Product(
										2L,
										"Headphones",
										"Jabra Elite Bluetooth Headphones",
										BigDecimal.valueOf(249.99)
						),
						new Product(
										3L,
										"Microsoft Surface Pro",
										"Microsoft Surface Pro 7 12.3\" 128GB Windows 10 Tablet With 10th Gen Intel Core i3/4GB RAM - Platinum",
										BigDecimal.valueOf(799.99)
						),
						new Product(
										4L,
										"Amazon Echo Dot",
										"Amazon Echo Dot (3rd Gen) with Alexa - Charcoal",
										BigDecimal.valueOf(34.99)
						)
		);
	}

	@Override
	public List<Product> findAllProducts() {
		return ALL_PRODUCTS();
	}
}
