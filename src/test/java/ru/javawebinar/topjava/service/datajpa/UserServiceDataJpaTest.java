package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collections;
import java.util.Date;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.assertMatch;

@ActiveProfiles(DATAJPA)
public class UserServiceDataJpaTest extends UserServiceTest {

    public UserServiceDataJpaTest() {
        super("User " + DATAJPA);
    }

    @Test
    public void getUserWithMealTest() {
        User user = service.getWithMeals(USER_ID);
        assertMatch(user, USER);
        MealTestData.assertMatch(user.getMeals(), MEALS);
    }

    @Test(expected = NotFoundException.class)
    public void getUserWithMealNotFoundTest() {
        User user = service.getWithMeals(123);
    }

    @Test
    public void getUserWithoutMealTest() {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1555, false, new Date(), Collections.singleton(Role.ROLE_USER));
        User created = service.create(newUser);
        newUser.setId(created.getId());
        int id = created.getId();
        assertMatch(service.getAll(), ADMIN, newUser, USER);
        User user = service.getWithMeals(id);
        assertMatch(user, newUser);
    }

}
