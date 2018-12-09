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
    private static final String ACTION_CREATE = "create";
    private static final String ACTION_DELETE = "delete";
    private static final Logger log = getLogger(MealServlet.class);
    private static final MealMemoryDatasetDao DATASET_DAO = new MealMemoryDatasetDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action != null) {
            switch (action.toLowerCase()) {
                case ACTION_ADD: {
                    log.debug("Open new form for adding new entry to the database");
                    req.getRequestDispatcher("addmeal.jsp").forward(req, resp);
                }
                break;
                case ACTION_CREATE: {
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
                break;
                case ACTION_EDIT: {
                    try {
                        long id = Integer.parseInt(req.getParameter("id"));
                        log.debug("Open form for edit entry with id = {}", id);
                        Meal meal = DATASET_DAO.findById(id);
                        if (meal != null) {
                            req.setAttribute("mealBeen", meal);
                            req.getRequestDispatcher("addmeal.jsp").forward(req, resp);
                        } else {
                            throw new Exception("Entry with id = " + id + " was not found");
                        }
                    } catch (Exception e) {
                        log.error("Entry was not edited: ", e);
                        resp.sendRedirect("meals");
                    }
                }
                break;
                case ACTION_DELETE: {
                    try {
                        DATASET_DAO.delete(Integer.parseInt(req.getParameter("id")));
                    } catch (Exception e) {
                        log.error("Entry was not deleted: ", e);
                    } finally {
                        resp.sendRedirect("meals");
                    }
                }
                break;
                default: {
                    resp.sendRedirect("meals");
                }
            }
        } else {
            log.error("Action not found!");
            resp.sendRedirect("meals");
        }
    }
}
