package com.tanvir.easymart.web;

import com.tanvir.easymart.domain.Cart;
import com.tanvir.easymart.domain.User;
import com.tanvir.easymart.repository.CartItemRepositoryImpl;
import com.tanvir.easymart.repository.CartRepositoryImpl;
import com.tanvir.easymart.repository.DummyProductRepositoryImpl;
import com.tanvir.easymart.service.CartService;
import com.tanvir.easymart.service.CartServiceImpl;
import com.tanvir.easymart.util.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-to-cart")
public class CartServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartServlet.class);

    private CartService cartService = new CartServiceImpl(new CartRepositoryImpl(),new DummyProductRepositoryImpl(),new CartItemRepositoryImpl());
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws IOException {
        var productId = req.getParameter("productId");
        LOGGER.info(
                "Received request to add product with id: {} to cart",
                productId);
        System.out.println("Received request to add product with id: { " + productId + "} to cart");
        var cart = getCart(req);
        cartService.addProductToCart(productId, cart);
        resp.sendRedirect("/home");
    }
    private Cart getCart(HttpServletRequest req) {
        final User currentUser
                = SecurityContext.getCurrentUser(req);
        return cartService.getCartByUser(currentUser);
    }
}
