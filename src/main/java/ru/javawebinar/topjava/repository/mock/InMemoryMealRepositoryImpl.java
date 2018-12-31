package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        for (int i = 0; i < MealsUtil.MEALS.size(); i++) {
            if (i < 3) {
                this.save(MealsUtil.MEALS.get(i), 2);
            } else {
                this.save(MealsUtil.MEALS.get(i), 1);
            }
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            return repository.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).put(meal.getId(), meal);
        }
        //return repository.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        return repository.get(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("get All");
        return new ArrayList<>(repository.get(userId).values()).stream()
            .sorted(Comparator.comparing(Meal::getDate).reversed())
            .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAllFiltered(int userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        if (startDate == null && startTime == null && endDate == null && endTime == null) {
            return getAll(userId);
        }
        return getAll(userId).stream()
            .filter(meal -> DateTimeUtil.isBetween(meal.getDate(),
                startDate != null ? startDate : LocalDate.MIN,
                endDate != null ? endDate : LocalDate.MAX)
                && DateTimeUtil.isBetween(meal.getTime(),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX)
            ).collect(Collectors.toList());
    }
}

