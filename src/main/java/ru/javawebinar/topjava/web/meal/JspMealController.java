package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends MealController {

    @GetMapping
    public String meals(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @PostMapping("/update/{id}")
    public String addOrUpdate(@PathVariable int id, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        if (id == -1) {
            Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
            create(meal);
        } else {
            Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
            update(meal, id);
        }
        return "redirect:/meals";
    }

    @GetMapping("/update/{id}")
    public String addOrUpdate(@PathVariable int id, Model model) {
        if (id == -1) {
            final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
            model.addAttribute("meal", meal);
        } else {
            model.addAttribute("meal", get(id));
        }
        return "/mealForm";
    }

    @GetMapping("/delete/{id}")
    public String deleteData(@PathVariable int id) {
        delete(id);
        return "redirect:/meals";
    }

    @PostMapping("/filter")
    public String filter(HttpServletRequest request) {
        log.debug("Filter StartDate: " + request.getParameter("startDate"));
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        request.setAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }
}
