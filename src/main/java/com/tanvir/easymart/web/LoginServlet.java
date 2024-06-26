package com.tanvir.easymart.web;

import com.tanvir.easymart.repository.JdbcUserRepositoryImpl;
import com.tanvir.easymart.service.UserService; // new import statement
import com.tanvir.easymart.domain.User;
import com.tanvir.easymart.dto.LoginDTO;
import com.tanvir.easymart.exceptions.UserNotFoundException;
import com.tanvir.easymart.service.UserServiceImpl;
import com.tanvir.easymart.util.SecurityContext;
import com.tanvir.easymart.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);

    @Inject
    private UserService userService;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("serving login page");
        String logout = req.getParameter("logout");
        if (logout != null && Boolean.parseBoolean(logout)){
            req.setAttribute("message", "You have been logged out successfully)");
        }
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var loginDTO = new LoginDTO(req.getParameter("username"), req.getParameter("password"));
        LOGGER.info("User trying to login with: {}", loginDTO);

        var errors = ValidationUtil.getInstance().validate(loginDTO);
        if (!errors.isEmpty()){
            LOGGER.info("Failed to login,sending login form again");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        }
        try {
            login(loginDTO,req);
            LOGGER.info("User logged in successfully, Redirecting to home page");
            resp.sendRedirect("/home");
        } catch (UserNotFoundException e){
            LOGGER.error("invalid username or password", e);
            errors.put("username","Invalid username or password");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        }
    }
    private void login(LoginDTO loginDTO, HttpServletRequest req) throws UserNotFoundException {
        User user = userService.verifyUser(loginDTO);
        SecurityContext.login(req, user);
    }
}

