package com.tanvir.easymart.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-to-cart")
public class CartServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartServlet.class);

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var productId = req.getParameter("productId");
        LOGGER.info("Received request to add product with id: {}", productId);

        var cart = getCart(req);
        addProductToCart(productId,cart);

        resp.sendRedirect("/home");
    }

    private void addProductToCart(String productId, Object cart) {
    }

    private Object getCart(HttpServletRequest req) {
        return null;
    }
}
