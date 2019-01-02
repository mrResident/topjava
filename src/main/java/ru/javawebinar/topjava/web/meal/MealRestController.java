package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;

@Controller
public class MealRestController {

    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealWithExceed> getAll() {
        log.info("getAll without filter");
        return MealsUtil.getWithExceeded(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getAllFiltered(String startDate, String startTime, String endDate, String endTime) {
        log.info("getAll with filter");
        try {
            return MealsUtil.getFilteredWithExceeded(service.getAllFiltered(
                authUserId(),
                meal -> DateTimeUtil.isBetween(meal.getDate(),
                    !startDate.isEmpty() ? LocalDate.parse(startDate) : LocalDate.MIN,
                    !endDate.isEmpty() ? LocalDate.parse(endDate) : LocalDate.MAX)),
                authUserCaloriesPerDay(),
                !startTime.isEmpty() ? LocalTime.parse(startTime) : LocalTime.MIN,
                !endTime.isEmpty() ? LocalTime.parse(endTime) : LocalTime.MAX);
        } catch (DateTimeParseException e) {
            log.info("getAll with filter: " + e.getMessage());
            return getAll();
        }
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }
}