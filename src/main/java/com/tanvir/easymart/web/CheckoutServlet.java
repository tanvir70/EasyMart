package com.tanvir.easymart.web;

import com.tanvir.easymart.repository.*;
import com.tanvir.easymart.service.CartService;
import com.tanvir.easymart.service.CartServiceImpl;
import com.tanvir.easymart.util.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutServlet.class);

    @Inject
    private CartService cartService;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Serving checkout page");
        var currentUser = SecurityContext.getCurrentUser(req);
        var cart = cartService.getCartByUser(currentUser);
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("/WEB-INF/checkout.jsp").forward(req, resp);
    }
}
