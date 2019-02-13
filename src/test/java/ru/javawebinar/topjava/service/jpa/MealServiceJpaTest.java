package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles(JPA)
public class MealServiceJpaTest extends MealServiceTest {

    static {
        String name = "Meal " + JPA;
        testName =  name + " ".repeat(15 - name.length());
    }

}
