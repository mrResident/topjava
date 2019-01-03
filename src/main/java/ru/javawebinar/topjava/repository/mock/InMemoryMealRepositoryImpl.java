package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
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
        return repository.get(userId) != null ? repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal) : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        return repository.get(userId) != null && repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        return repository.get(userId) != null ? repository.get(userId).get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("get All");
        return getAllRepo(userId, meal -> true);
    }

    @Override
    public Collection<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("get All filtered");
        return getAllRepo(userId, meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate));
    }

    private Collection<Meal> getAllRepo(int userId, Predicate<Meal> filter) {
        return repository.get(userId) != null ? new ArrayList<>(repository.get(userId).values()).stream()
            .sorted(Comparator.comparing(Meal::getDate).reversed())
            .filter(filter)
            .collect(Collectors.toList()) : new ArrayList<>();
    }
}

