package ru.javawebinar.topjava.dataset;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.CRUD;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class MemoryDataset<T> implements CRUD<T> {

    private static final Logger log = getLogger(MemoryDataset.class);
    protected final AtomicLong counter = new AtomicLong(0);
    protected final ConcurrentMap<Long, T> dataSource = new ConcurrentHashMap<>();

    @Override
    public T findById(final long id) {
        return dataSource.getOrDefault(id, null);
    }

    @Override
    public void delete(final long id) {
        if (dataSource.remove(id) != null) {
            log.debug("[deleteEntry] Entry deleted sucsesfull.");
        } else {
            log.error("[deleteEntry] Entry with id={} not found", id);
        }
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(dataSource.values());
    }
}
