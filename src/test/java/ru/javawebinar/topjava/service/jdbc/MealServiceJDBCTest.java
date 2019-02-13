package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class MealServiceJDBCTest extends MealServiceTest {

    static {
        String name = "Meal " + JDBC;
        testName =  name + " ".repeat(15 - name.length());
    }

}
