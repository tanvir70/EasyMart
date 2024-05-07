package com.tanvir.easymart.service;

import com.tanvir.easymart.domain.Cart;
import com.tanvir.easymart.domain.CartItem;
import com.tanvir.easymart.domain.Product;
import com.tanvir.easymart.domain.User;

import com.tanvir.easymart.exceptions.ProductNotFoundException;
import com.tanvir.easymart.repository.CartItemRepository;
import com.tanvir.easymart.repository.CartRepository;
import com.tanvir.easymart.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;

public class CartServiceImpl implements CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;


    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
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

    public void addProductToCart(String productId, Cart cart) {
        if (productId == null || productId.length() == 0) {
            throw new IllegalArgumentException("Product id is required");
        }
        Long id = parseProductId(productId);

        var product = productRepository.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        addProductToCart(product, cart);

        Integer totalTotalItem = getTotalItem(cart);
        BigDecimal totalPrice = calculateTotalPrice(cart);
        cart.setTotalItem(totalTotalItem);
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
    }

    private void addProductToCart(Product product, Cart cart) {
        var cartItemOptional
                = findSimilarProductInCart(cart, product);
        var cartItem = cartItemOptional
                .map(this::increaseQuantityByOne)
                .orElseGet(() ->
                        createNewShoppingCartItem(product));
        cart.getCartItems().add(cartItem);
    }

    private CartItem createNewShoppingCartItem(Product product) {
        var cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setPrice(product.getPrice());
        return cartItemRepository.save(cartItem);
    }

    private CartItem increaseQuantityByOne(CartItem cartItem) {
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        BigDecimal totalPrice = cartItem.getProduct()
                .getPrice()
                .multiply(BigDecimal.valueOf(
                        cartItem.getQuantity()));
        cartItem.setPrice(totalPrice);
        return cartItemRepository.update(cartItem);
    }

    private Optional<CartItem> findSimilarProductInCart(
            Cart cart, Product product) {
        return cart.getCartItems()
                .stream().filter(cartItem ->
                        cartItem.getProduct().equals(product))
                .findFirst();
    }

    private Integer getTotalItem(Cart cart) {
        return cart.getCartItems()
                .stream().map(CartItem::getQuantity)
                .reduce(0, Integer::sum);
    }

    private BigDecimal calculateTotalPrice(Cart cart) {
        return cart.getCartItems()
                .stream().map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Long parseProductId(String productId) {
        try {
            return Long.parseLong(productId);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                    "Product id must be a number", ex);
        }
    }
}
