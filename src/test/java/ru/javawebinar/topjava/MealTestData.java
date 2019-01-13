package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_MEAL_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 8;

    public static final Meal USER_MEAL_1 = new Meal(USER_MEAL_ID, LocalDateTime.of(2019, Month.JANUARY, 8, 9, 0), "Завтрак", 300);
    public static final Meal USER_MEAL_2 = new Meal(USER_MEAL_ID + 1, LocalDateTime.of(2019, Month.JANUARY, 8, 12, 0), "Обед", 1000);
    public static final Meal USER_MEAL_3 = new Meal(USER_MEAL_ID + 2, LocalDateTime.of(2019, Month.JANUARY, 8, 18, 0), "Ужин", 700);
    public static final Meal USER_MEAL_4 = new Meal(USER_MEAL_ID + 3, LocalDateTime.of(2019, Month.JANUARY, 10, 8, 0), "Завтрак", 500);
    public static final Meal USER_MEAL_5 = new Meal(USER_MEAL_ID + 4, LocalDateTime.of(2019, Month.JANUARY, 10, 11, 45), "Обед", 1000);
    public static final Meal USER_MEAL_6 = new Meal(USER_MEAL_ID + 5, LocalDateTime.of(2019, Month.JANUARY, 10, 19, 20), "Ужин", 700);

    public static final Meal ADMIN_MEAL_1 = new Meal(ADMIN_MEAL_ID, LocalDateTime.of(2019, Month.JANUARY, 15, 9, 30), "Завтрак", 500);
    public static final Meal ADMIN_MEAL_2 = new Meal(ADMIN_MEAL_ID + 1, LocalDateTime.of(2019, Month.JANUARY, 15, 12, 10), "Обед", 1000);
    public static final Meal ADMIN_MEAL_3 = new Meal(ADMIN_MEAL_ID + 2, LocalDateTime.of(2019, Month.JANUARY, 15, 18, 20), "Ужин", 700);

    public static final List<Meal> ADMIN_MEAL_LIST = Arrays.asList(ADMIN_MEAL_3, ADMIN_MEAL_2, ADMIN_MEAL_1);

    public static Meal getCreatedEntity() {
        return new Meal(null ,LocalDateTime.of(2019, Month.JANUARY, 16, 9, 0), "new Admin breakfast", 300);
    }

    public static Meal getUpdatedEntity() {
        return new Meal(ADMIN_MEAL_ID, LocalDateTime.of(2019, Month.JANUARY, 15, 9, 30), "updated Завтрак", 500);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

}
