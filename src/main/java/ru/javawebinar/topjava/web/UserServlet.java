package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to users");
        req.getRequestDispatcher("/users.jsp").forward(req, resp);
        resp.sendRedirect("users.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        log.info("Authorized user with ID = {}", req.getParameter("userId"));
        try {
            SecurityUtil.setAuthUserId(Integer.parseInt(req.getParameter("userId")));
        } catch (NumberFormatException e) {
            SecurityUtil.setAuthUserId(1);
        }
        resp.sendRedirect("meals");
    }
}
