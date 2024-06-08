package com.tanvir.easymart.web;

import com.tanvir.easymart.domain.Cart;
import com.tanvir.easymart.dto.ProductDTO;
import com.tanvir.easymart.repository.*;
import com.tanvir.easymart.service.CartService;
import com.tanvir.easymart.service.CartServiceImpl;
import com.tanvir.easymart.service.ProductService;
import com.tanvir.easymart.service.ProductServiceImpl;
import com.tanvir.easymart.util.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private static final Logger LOGGER =  LoggerFactory.getLogger(HomeServlet.class);
    private ProductService productService = new ProductServiceImpl(new JdbcProductRepositoryImpl());
    private CartService cartService
            = new CartServiceImpl(new JdbcCartRepositoryImpl(),
            new JdbcProductRepositoryImpl(),
            new JdbcCartItemRepositoryImpl());

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LOGGER.info("Serving home page");
        final String attribute = req.getParameter("orderSuccess");
        if (attribute != null && Boolean.parseBoolean(attribute)) {
            req.setAttribute("message",
                    "<strong>Congratulation!</strong> You're order has been placed successfully. ");
        }

        List<ProductDTO> allProducts = productService.findAllProductSortedByName();
        LOGGER.info("Total product found {}", allProducts.size());

        Cart cart = cartService.getCartByUser(SecurityContext.getCurrentUser(req));
        if (SecurityContext.isAuthenticated(req)) {
            var currentUser = SecurityContext.getCurrentUser(req);
            cart = cartService.getCartByUser(currentUser);
            req.setAttribute("cart", cart);
        }

        req.setAttribute("products", allProducts);
        req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);
    }
}
