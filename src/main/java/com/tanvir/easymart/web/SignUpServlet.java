package com.tanvir.easymart.web;// presentation layer

import com.tanvir.easymart.dto.UserDTO;
import com.tanvir.easymart.repository.UserRepository;
import com.tanvir.easymart.repository.UserRepositoryImpl;
import com.tanvir.easymart.service.UserService;
import com.tanvir.easymart.service.UserServiceImpl;
import com.tanvir.easymart.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private final static Logger LOGGER = LoggerFactory.getLogger(SignUpServlet.class);
    private UserService userService = new UserServiceImpl(new UserRepositoryImpl());


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("serving signup page");
        req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = copyParametersTo(req);
        var errors = ValidationUtil.getInstance().validate(userDTO);
        if (!errors.isEmpty()) {
            req.setAttribute("userDto",userDTO);
            req.setAttribute("errors",errors);
            LOGGER.info("User sent invalid data: {}", userDTO);
            req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req,resp);
        } else if(userService.inNotUniqueUserName(userDTO)) {
            LOGGER.info("Username: {} is already exits.", userDTO.getUsername());
            errors.put("username","The username already exits. Please use different username");
            req.setAttribute("userDto",userDTO);
            req.setAttribute("errors",errors);
            req.getRequestDispatcher(
                    "/WEB-INF/signup.jsp").forward(req, resp);
        }else {
            LOGGER.info("user is valid, creating a new user with: {}",userDTO);
            userService.saveUser(userDTO);
            resp.sendRedirect("/login");
        }
    }

    private UserDTO copyParametersTo(HttpServletRequest req) {
        var userDTO = new UserDTO();
        userDTO.setFirstName(req.getParameter("firstName"));
        userDTO.setLastName(req.getParameter("lastName"));
        userDTO.setPassword(req.getParameter("password"));
        userDTO.setPasswordConfirmed(req.getParameter(
                "passwordConfirmed"));
        userDTO.setEmail(req.getParameter("email"));
        userDTO.setUsername(req.getParameter("username"));

        return userDTO;
    }
}

