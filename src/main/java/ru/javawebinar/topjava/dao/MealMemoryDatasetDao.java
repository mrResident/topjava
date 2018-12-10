package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.slf4j.LoggerFactory.getLogger;

public class MealMemoryDatasetDao implements CRUD<Meal> {

    private static final Logger log = getLogger(MealMemoryDatasetDao.class);
    private final AtomicLong counter = new AtomicLong(0);
    private final ConcurrentMap<Long, Meal> dataSource = new ConcurrentHashMap<>();

    public MealMemoryDatasetDao() {
    }

    @Override
    public Meal create(final Meal meal) {
        if (meal == null) {
            log.error("[createOrUpdate] dataSource not changed, because input data is null.");
            return null;
        }
        long newID = counter.addAndGet(1);
        meal.setId(newID);
        dataSource.put(newID, meal);
        log.debug("[create] New entry was created sucsesfull.");
        return meal;
    }

    @Override
    public void update(final Meal meal) {
        if (meal != null && dataSource.replace(meal.getId(), meal) != null) {
            log.debug("[update] Entry with id = {} was updated in dataSource.", meal.getId());
        } else {
            log.debug("[update] Entry was not updated in dataSource!");
        }
    }

    @Override
    public void delete(final long id) {
        if (dataSource.remove(id) != null) {
            log.debug("[delete] Entry with id = {} deleted sucsesfull.", id);
        } else {
            log.error("[delete] Entry with id={} not found", id);
        }
    }

    @Override
    public Meal findById(final long id) {
        return dataSource.get(id);
    }

    @Override
    public List<Meal> findAll() {
        return new ArrayList<>(dataSource.values());
    }
}
