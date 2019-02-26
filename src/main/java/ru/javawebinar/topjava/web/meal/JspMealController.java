package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController extends MealController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("/meals")
    public String meals(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping("/meals/add")
    public String addMeal(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "/mealForm";
    }

    @PostMapping("/meals/add")
    public String createMeal(HttpServletRequest request) {
        Meal meal = new Meal(
            LocalDateTime.parse(request.getParameter("dateTime")),
            request.getParameter("description"),
            Integer.parseInt(request.getParameter("calories")));
        create(meal);
        return "redirect:/meals";
    }

    @PostMapping("/meals/update/{id}")
    public String updateMeal(@PathVariable int id, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
            LocalDateTime.parse(request.getParameter("dateTime")),
            request.getParameter("description"),
            Integer.parseInt(request.getParameter("calories")));
        update(meal, id);
        return "redirect:/meals";
    }

    @GetMapping("/meals/update/{id}")
    public String updateMeal(@PathVariable int id, Model model) {
        model.addAttribute("meal", get(id));
        return "/mealForm";
    }

    @GetMapping("/meals/delete/{id}")
    public String deleteMeal(@PathVariable int id, Model model) {
        delete(id);
        return "redirect:/meals";
    }

    @PostMapping("/filter")
    public String filterMeal(HttpServletRequest request) {
        log.debug("Filter StartDate: " + request.getParameter("startDate"));
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        request.setAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }
}
