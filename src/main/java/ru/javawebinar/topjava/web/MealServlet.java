package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealWithExceed> mealWithExceedList = MealsUtil.getFilteredWithExceeded(MealsUtil.MEALS, LocalTime.MIN, LocalTime.MAX, 2000);
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        log.debug("Getting list meals with exceed and forwarding to meals.jsp page");
        request.setAttribute("mealList", mealWithExceedList);
        request.setAttribute("dtFormatter", dtFormatter);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
