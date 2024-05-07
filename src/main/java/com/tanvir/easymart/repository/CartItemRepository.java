package com.tanvir.easymart.repository;

import com.tanvir.easymart.domain.CartItem;

public interface CartItemRepository {
    CartItem save(CartItem cartItem);
    CartItem update(CartItem cartItem);
}
