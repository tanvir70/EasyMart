package com.tanvir.easymart.repository;

import com.tanvir.easymart.domain.Cart;
import com.tanvir.easymart.domain.User;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findByUser(User currentUser);

    Cart save(Cart cart);

    Cart update(Cart cart);

    Optional<Cart> findOne(long cartId);
}

