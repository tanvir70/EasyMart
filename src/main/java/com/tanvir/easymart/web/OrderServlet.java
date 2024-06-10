package com.tanvir.easymart.web;

import com.tanvir.easymart.dto.ShippingAddressDTO;
import com.tanvir.easymart.repository.*;
import com.tanvir.easymart.service.CartService;
import com.tanvir.easymart.service.CartServiceImpl;
import com.tanvir.easymart.service.OrderService;
import com.tanvir.easymart.service.OrderServiceImpl;
import com.tanvir.easymart.util.SecurityContext;
import com.tanvir.easymart.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServlet.class);

    private CartService cartService = new CartServiceImpl(
            new JdbcCartRepositoryImpl(),
            new JdbcProductRepositoryImpl(),
            new JdbcCartItemRepositoryImpl());
    private OrderService orderService
            = new OrderServiceImpl(new JdbcOrderRepositoryImpl(),
            new JdbcShippingAddressRepositoryImpl(),
            new JdbcCartRepositoryImpl());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("OrderServlet doGet() called");
        addCartToUi(request);
        request.setAttribute("countries", getCountries());
        request.getRequestDispatcher("/WEB-INF/order.jsp").forward(request, response);
    }

    private void addCartToUi(HttpServletRequest request) {
        if (SecurityContext.isAuthenticated(request)) {
            var currentUser = SecurityContext.getCurrentUser(request);
            var cart = cartService.getCartByUser(currentUser);
            request.setAttribute("cart", cart);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Handle order request form");
        var shippingAddress = copyParametersTo(req);
        LOGGER.info("shippingAddress information: {}", Optional.ofNullable(shippingAddress));
        var errors = ValidationUtil.getInstance()
                .validate(shippingAddress);
        if (!errors.isEmpty()) {
            req.setAttribute("countries", getCountries());
            req.setAttribute("errors", errors);
            req.setAttribute("shippingAddress", shippingAddress);
            addCartToUi(req);
            req.getRequestDispatcher("/WEB-INF/order.jsp ")
                    .forward(req, resp);
        } else {
            orderService.processOrder(shippingAddress,
                    SecurityContext.getCurrentUser(req));
            resp.sendRedirect("/home?orderSuccess=true");
        }
    }

    private ShippingAddressDTO copyParametersTo(HttpServletRequest req) {
        var shippingAddress = new ShippingAddressDTO();
        shippingAddress.setAddress(req.getParameter("address"));
        shippingAddress.setAddress2(req.getParameter("address2"));
        shippingAddress.setState(req.getParameter("state"));
        shippingAddress.setZip(req.getParameter("zip"));
        shippingAddress.setCountry(req.getParameter("country"));
        shippingAddress.setMobileNumber(req.getParameter("mobileNumber"));
        return shippingAddress;
    }

    private List<String> getCountries() {
        return List.of("Bangladesh", "Japan", "Pakistan", "Sri Lanka");
    }
}