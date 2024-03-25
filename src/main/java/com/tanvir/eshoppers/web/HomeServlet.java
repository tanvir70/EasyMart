package com.tanvir.eshoppers.web;

import com.tanvir.eshoppers.dto.ProductDTO;
import com.tanvir.eshoppers.repository.DummyProductRepositoryImpl;
import com.tanvir.eshoppers.service.ProductService;
import com.tanvir.eshoppers.service.ProductServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {


    private ProductService productService = new ProductServiceImpl(new DummyProductRepositoryImpl());

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<ProductDTO> allProducts = productService.findAllProductSortedByName();
        req.setAttribute("products", allProducts);
        req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);
    }
}
