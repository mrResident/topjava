package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Override
    @Transactional
    Meal save(Meal meal);

    @Transactional
    int deleteByIdAndUser_Id(int id, int userId);

    Meal getByIdAndUser_Id(int id, int userId);

    List<Meal> findByUser_IdOrderByDateTimeDesc(int userId);

    List<Meal> findByDateTimeBetweenAndUser_IdOrderByDateTimeDesc(LocalDateTime startDate, LocalDateTime endDate, int userId);

}
