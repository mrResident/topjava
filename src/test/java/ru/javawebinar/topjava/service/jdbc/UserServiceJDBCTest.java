package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class UserServiceJDBCTest extends UserServiceTest {

    static {
        String name = "User " + JDBC;
        testName =  name + " ".repeat(15 - name.length());
    }

}
