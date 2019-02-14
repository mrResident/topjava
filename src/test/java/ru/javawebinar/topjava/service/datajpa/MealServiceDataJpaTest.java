package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class MealServiceDataJpaTest extends MealServiceTest {

    public MealServiceDataJpaTest() {
        super("Meal " + DATAJPA);
    }

    @Test
    public void getMealWithUserTest() {
        Meal meal = service.getWithUser(MealTestData.MEAL1_ID, UserTestData.USER_ID);
        MealTestData.assertMatch(meal, MealTestData.MEAL1);
        UserTestData.assertMatch(meal.getUser(), UserTestData.USER);
    }

    @Test(expected = NotFoundException.class)
    public void getMealWithUserNotFoundTest() {
        Meal meal = service.getWithUser(MealTestData.MEAL1_ID, 123);
    }
}
