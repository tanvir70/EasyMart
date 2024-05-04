package com.tanvir.easymart.util;

import com.tanvir.easymart.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SecurityContext {
    public static final String AUTHENTICATED_KEY = "auth.key";

    public static void login(HttpServletRequest request, User user){
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null){
            oldSession.invalidate();
        }
        HttpSession session = request.getSession(true);
        session.setAttribute(AUTHENTICATED_KEY, user);
    }
    public static void logout(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        session.removeAttribute(AUTHENTICATED_KEY);
    }

    public static User getCurrentUser(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        return (User) session.getAttribute(AUTHENTICATED_KEY);
    }

    public static boolean isAuthenticated(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        return  session.getAttribute(AUTHENTICATED_KEY) != null;
    }
}
