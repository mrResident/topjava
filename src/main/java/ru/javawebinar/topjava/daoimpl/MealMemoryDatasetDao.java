package ru.javawebinar.topjava.daoimpl;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dataset.MemoryDataset;
import ru.javawebinar.topjava.model.Meal;

import static org.slf4j.LoggerFactory.getLogger;

public class MealMemoryDatasetDao extends MemoryDataset<Meal> {

    private static final Logger log = getLogger(MealMemoryDatasetDao.class);
    private static MealMemoryDatasetDao instance;

    private MealMemoryDatasetDao() {
    }

    public static MealMemoryDatasetDao getInstance() {
        if (instance == null) {
            instance = new MealMemoryDatasetDao();
        }
        return instance;
    }

    @Override
    public void create(final Meal data) {
        createOrUpdate(data);
    }

    @Override
    public void update(final Meal data) {
        createOrUpdate(data);
    }

    private void createOrUpdate(final Meal data) {
        if (data == null) {
            log.error("[createOrUpdate] dataSource not changed, because input data is null");
            return;
        }
        if (data.getId() == 0) {
            createNewEntry(data);
        } else {
            updateEntry(data);
        }
    }

    private void createNewEntry(final Meal data) {
        long newID = counter.addAndGet(1);
        data.setId(newID);
        dataSource.put(newID, data);
        log.debug("[createNewEntry] New entry was created sucsesfull");
    }

    private void updateEntry(final Meal data) {
        if (dataSource.put(data.getId(), data) != null) {
            log.debug("[updateEntry] Input data was updated in dataSource.");
        } else {
            log.debug("[updateEntry] Input data with id={} was created in dataSource, because entry with id={} not found!", data.getId(), data.getId());
        }
    }
}
