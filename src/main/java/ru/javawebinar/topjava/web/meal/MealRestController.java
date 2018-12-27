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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private Map<String, LocalDate> filterDate = new HashMap<>();
    private Map<String, LocalTime> filterTime = new HashMap<>();

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
        filterDate.put(MealRestController.START_DATE, null);
        filterDate.put(MealRestController.END_DATE, null);
        filterTime.put(MealRestController.START_TIME, null);
        filterTime.put(MealRestController.END_TIME, null);
    }

    public List<MealWithExceed> getAll() {
        log.info("getAll without filter");
        return MealsUtil.getWithExceeded(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getAllFiltered() {
        log.info("getAll with filter");
        if (filterDate.get(MealRestController.START_DATE) == null
            && filterDate.get(MealRestController.END_DATE) == null
            && filterTime.get(MealRestController.START_TIME) == null
            && filterTime.get(MealRestController.END_TIME) == null) {
            return getAll();
        }
        return MealsUtil.getFilteredWithExceeded(
            service.getAll(authUserId()),
            authUserCaloriesPerDay(),
            filterTime.get(MealRestController.START_TIME) != null ? filterTime.get(MealRestController.START_TIME) : LocalTime.MIN,
            filterTime.get(MealRestController.END_TIME) != null ? filterTime.get(MealRestController.END_TIME) : LocalTime.MAX
        ).stream()
            .filter(mwe -> DateTimeUtil.isBetween(
                mwe.getDateTime().toLocalDate(),
                filterDate.get(MealRestController.START_DATE) != null ? filterDate.get(MealRestController.START_DATE) : LocalDate.MIN,
                filterDate.get(MealRestController.END_DATE) != null ? filterDate.get(MealRestController.END_DATE) : LocalDate.MAX))
            .collect(Collectors.toList());
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

    public LocalDate getFilterDate(String key) {
        log.info("Get filter for date: key {} value {}", key, filterDate.get(key));
        return filterDate.get(key);
    }

    public void setFilterDate(String key, String value) {
        log.info("Set filter for date: key {} value {}", key, value);
        try {
            filterDate.put(key, LocalDate.parse(value));
        } catch (DateTimeParseException e) {
            log.error("Can not parse date", e.getMessage());
            filterDate.put(key, null);
        }
    }

    public LocalTime getFilterTime(String key) {
        log.info("Get filter for time: key {} value {}", key, filterTime.get(key));
        return filterTime.get(key);
    }

    public void setFilterTime(String key, String value) {
        log.info("Set filter for time: key {} value {}", key, value);
        try {
            filterTime.put(key, LocalTime.parse(value));
        } catch (DateTimeParseException e) {
            log.error("Can not parse time", e.getMessage());
            filterTime.put(key, null);
        }
    }
}