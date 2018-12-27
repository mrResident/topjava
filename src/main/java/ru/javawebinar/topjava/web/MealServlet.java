package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final String SPRING_CONFIG = "spring/spring-app.xml";

    private ConfigurableApplicationContext appCtx;
    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext(SPRING_CONFIG);
        mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()) {
            mealRestController.create(meal);
        } else {
            mealRestController.update(meal, meal.getId());
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "reset_date_time_filter":
                log.debug("Reset filter");
                mealRestController.setFilterDate(MealRestController.START_DATE, "");
                mealRestController.setFilterDate(MealRestController.END_DATE, "");
                mealRestController.setFilterTime(MealRestController.START_TIME, "");
                mealRestController.setFilterTime(MealRestController.END_TIME, "");
                response.sendRedirect("meals");
                break;
            case "set_date_time_filter":
                log.debug("Set date/time filter");
                mealRestController.setFilterDate(MealRestController.START_DATE, request.getParameter(MealRestController.START_DATE));
                mealRestController.setFilterDate(MealRestController.END_DATE, request.getParameter(MealRestController.END_DATE));
                mealRestController.setFilterTime(MealRestController.START_TIME, request.getParameter(MealRestController.START_TIME));
                mealRestController.setFilterTime(MealRestController.END_TIME, request.getParameter(MealRestController.END_TIME));
            case "all":
            default:
                log.info("getAll");
                request.setAttribute(MealRestController.START_DATE, mealRestController.getFilterDate(MealRestController.START_DATE));
                request.setAttribute(MealRestController.END_DATE, mealRestController.getFilterDate(MealRestController.END_DATE));
                request.setAttribute(MealRestController.START_TIME, mealRestController.getFilterTime(MealRestController.START_TIME));
                request.setAttribute(MealRestController.END_TIME, mealRestController.getFilterTime(MealRestController.END_TIME));
                request.setAttribute("meals", mealRestController.getAllFiltered());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }
}
