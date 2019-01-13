package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
    "classpath:spring/spring-app.xml",
    "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService mealService;

    @Test
    public void create() {
        Meal newMeal = getCreatedEntity();
        mealService.create(newMeal, ADMIN_ID);
        assertMatch(mealService.getAll(ADMIN_ID), newMeal, ADMIN_MEAL_3, ADMIN_MEAL_2, ADMIN_MEAL_1);
    }

    @Test
    public void update() throws Exception {
        Meal updatedMeal = getUpdatedEntity();
        mealService.update(updatedMeal, ADMIN_ID);
        assertMatch(mealService.get(ADMIN_MEAL_ID,ADMIN_ID), updatedMeal);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        Meal updatedMeal = getUpdatedEntity();
        mealService.update(updatedMeal, USER_ID);
    }

    @Test
    public void get() throws Exception {
        Meal actual = mealService.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        mealService.get(USER_MEAL_ID, ADMIN_ID);
    }

    @Test
    public void getBetween() throws Exception {
        List<Meal> actual = mealService.getBetweenDates(
            LocalDate.of(2019, Month.JANUARY, 1),
            LocalDate.of(2019, Month.JANUARY, 8),
            USER_ID
        );
        assertMatch(actual, USER_MEAL_3, USER_MEAL_2, USER_MEAL_1);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> actual = mealService.getAll(ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL_LIST);
    }

    @Test
    public void delete() throws Exception {
        mealService.delete(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(mealService.getAll(ADMIN_ID), ADMIN_MEAL_3, ADMIN_MEAL_2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        mealService.delete(USER_MEAL_ID, ADMIN_ID);
    }

}