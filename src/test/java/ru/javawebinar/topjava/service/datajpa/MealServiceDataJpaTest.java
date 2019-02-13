package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class MealServiceDataJpaTest extends MealServiceTest {

    static {
        String name = "Meal " + DATAJPA;
        testName =  name + " ".repeat(15 - name.length());
    }

}
