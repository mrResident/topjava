package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealMemoryDatasetDao;
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

    private static final String ACTION_ADD = "add";
    private static final String ACTION_EDIT = "edit";
    private static final String ACTION_DELETE = "delete";
    private static final Logger log = getLogger(MealServlet.class);
    private static final MealMemoryDatasetDao DATASET_DAO = new MealMemoryDatasetDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            long id = 0;
            if (request.getParameter("id") != null) {
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (Exception e) {
                    log.error("Invalid ID: ", e);
                }
            }
            switch (action.toLowerCase()) {
                case ACTION_ADD: {
                    log.debug("Open new form for adding new entry to the database");
                    request.getRequestDispatcher("addmeal.jsp").forward(request, response);
                }
                break;
                case ACTION_EDIT: {
                    try {
                        log.debug("Open form for edit entry with id = {}", id);
                        Meal meal = DATASET_DAO.findById(id);
                        if (meal != null) {
                            request.setAttribute("mealBean", meal);
                            request.getRequestDispatcher("addmeal.jsp").forward(request, response);
                        } else {
                            throw new Exception("Entry with id = " + id + " was not found");
                        }
                    } catch (Exception e) {
                        log.error("Entry was not edited: ", e);
                    }
                }
                break;
                case ACTION_DELETE: {
                    DATASET_DAO.delete(id);
                    response.sendRedirect("meals");
                }
                break;
                default: {
                    response.sendRedirect("meals");
                }
            }
        } else {
            List<MealWithExceed> mealWithExceedList = MealsUtil.getFilteredWithExceeded(
                DATASET_DAO.findAll(),
                LocalTime.MIN,
                LocalTime.MAX,
                2000
            );
            DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            request.setAttribute("mealList", mealWithExceedList);
            request.setAttribute("dtFormatter", dtFormatter);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        try {
                Meal meal = new Meal(
                    LocalDateTime.parse(req.getParameter("datetime-local")),
                    req.getParameter("description"),
                    Integer.parseInt(req.getParameter("calories"))
                );
                if (!req.getParameter("id_edit").isEmpty()) {
                    long id = Integer.parseInt(req.getParameter("id_edit"));
                    meal.setId(id);
                    DATASET_DAO.update(meal);
                } else {
                    DATASET_DAO.create(meal);
                }
        } catch (Exception e) {
            log.error("Entry was not created/updated: ", e);
        } finally {
            resp.sendRedirect("meals");
        }
    }
}
