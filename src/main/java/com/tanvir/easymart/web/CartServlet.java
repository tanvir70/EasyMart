package com.tanvir.easymart.web;

import com.tanvir.easymart.domain.Cart;
import com.tanvir.easymart.domain.User;
import com.tanvir.easymart.repository.*;
import com.tanvir.easymart.service.Action;
import com.tanvir.easymart.service.CartService;
import com.tanvir.easymart.service.CartServiceImpl;
import com.tanvir.easymart.util.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tanvir.easymart.util.StringUtil;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-to-cart")
public class CartServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartServlet.class);

    @Inject
    private CartService cartService;

    protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        var productId = req.getParameter("productId");
        LOGGER.info("Received request to add product with id: {} to cart", productId);

        System.out.println("Received request to add product with id: { " + productId + "} to cart");

        var action = req.getParameter("action");
        var cart = getCart(req);

        if(StringUtil.isNotEmpty(action)){
            processCart(productId, action, cart);
            resp.sendRedirect("/checkout");
            return;
        }

        LOGGER.info("Received request to add product with id: {} to cart", productId);

        cartService.addProductToCart(productId, cart);
        resp.sendRedirect("/home");

    }

    private void processCart(String productId, String action, Cart cart) {
        switch (Action.valueOf(action.toUpperCase())){
            case ADD:
                LOGGER.info("Received request to add product with id: {} to cart", productId);
                cartService.addProductToCart(productId, cart);
                break;
            case REMOVE:
                LOGGER.info("Received request to remove product with id: {} from cart", productId);
                cartService.removeProductToCart(productId, cart);
                break;
            case REMOVAL:
                LOGGER.info("Received request to remove all products from cart");
                cartService.removeAllProductsFromCart(productId, cart);
                break;
        }

    }

    private Cart getCart(HttpServletRequest req) {
        final User currentUser
                = SecurityContext.getCurrentUser(req);
        return cartService.getCartByUser(currentUser);
    }
}
