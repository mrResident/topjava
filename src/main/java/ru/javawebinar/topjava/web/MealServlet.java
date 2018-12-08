package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.daoimpl.MealMemoryDatasetDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealWithExceed> mealWithExceedList = MealsUtil.getFilteredWithExceeded(
            MealMemoryDatasetDao.getInstance().findAll(),
            LocalTime.MIN,
            LocalTime.MAX,
            2000
        );
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        request.setAttribute("mealList", mealWithExceedList);
        request.setAttribute("dtFormatter", dtFormatter);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getParameter("add") != null) {
            log.debug("Open new form for adding new entry to the database");
            req.getRequestDispatcher("addmeal.jsp").forward(req, resp);
        }
        if (req.getParameter("create") != null) {
            try {
                Meal meal = new Meal(
                    LocalDateTime.parse(req.getParameter("datetime-local")),
                    req.getParameter("description"),
                    Integer.parseInt(req.getParameter("calories"))
                );
                if (!req.getParameter("id_edit").isEmpty()) {
                    meal.setId(Integer.parseInt(req.getParameter("id_edit")));
                    MealMemoryDatasetDao.getInstance().update(meal);
                } else {
                    MealMemoryDatasetDao.getInstance().create(meal);
                }
            } catch (Exception e) {
                log.error("Entry was not created: ", e);
            } finally {
                resp.sendRedirect("meals");
            }
        }
        if (req.getParameter("delete") != null) {
            try {
                MealMemoryDatasetDao.getInstance().delete(Integer.parseInt(req.getParameter("id")));
            } catch (Exception e) {
                log.error("Entry was not deleted: ", e);
            } finally {
                resp.sendRedirect("meals");
            }
        }
        if (req.getParameter("edit") != null) {
            try {
                log.debug("Open form for edit entry with id = {}", Integer.parseInt(req.getParameter("id")));
                req.getRequestDispatcher("addmeal.jsp").forward(req, resp);
            } catch (Exception e) {
                log.error("Entry was not edited: ", e);
                resp.sendRedirect("meals");
            }
        }
    }
}
