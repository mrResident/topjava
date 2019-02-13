package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles(JPA)
public class UserServiceJpaTest extends UserServiceTest {

    static {
        String name = "User " + JPA;
        testName =  name + " ".repeat(15 - name.length());
    }

}
