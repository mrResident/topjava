package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class UserServiceDataJpaTest extends UserServiceTest {

    static {
        String name = "User " + DATAJPA;
        testName =  name + " ".repeat(15 - name.length());
    }

}
