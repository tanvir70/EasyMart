package com.tanvir.easymart.service;

import com.tanvir.easymart.domain.Cart;
import com.tanvir.easymart.domain.User;

public interface CartService {
    Cart getCartByUser(User currentUser);

    void addProductToCart(String productId, Cart cart);
}
