package com.tanvir.easymart.service;

import com.tanvir.easymart.domain.Cart;
import com.tanvir.easymart.domain.User;

import com.tanvir.easymart.repository.CartRepository;

import java.util.Optional;

public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

   public Cart getCartByUser(User currentUser) {
       Optional<Cart> optionalCart = cartRepository.findByUser(currentUser);
        return optionalCart.orElseGet(() ->
             createNewCart(currentUser));
    }

    private Cart createNewCart(User currentUser) {
       Cart cart = new Cart();
            cart.setUser(currentUser);
            return cartRepository.save(cart);
    }

}
