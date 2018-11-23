package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2018, Month.NOVEMBER, 21,14,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2018, Month.NOVEMBER, 22,14,0), "Обед", 1200),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2018, Month.NOVEMBER, 21,19,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2018, Month.NOVEMBER, 22,9,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2018, Month.NOVEMBER, 21,9,0), "Завтрак", 1200)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).forEach(System.out::println);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Optional<Integer>> values = mealList.stream()
            .collect(Collectors.groupingBy(
                (uu) -> uu.getDateTime().toLocalDate(),
                Collectors.mapping(
                    UserMeal::getCalories,
                    Collectors.reducing((i1, i2) -> i1 + i2))
            ));
        return mealList.stream()
            .filter((umwe) -> TimeUtil.isBetween(umwe.getDateTime().toLocalTime(), startTime, endTime))
            .map((um) -> new UserMealWithExceed(
                um.getDateTime(),
                um.getDescription(),
                um.getCalories(),
                values.get(um.getDateTime().toLocalDate()).orElse(0) > caloriesPerDay))
            .collect(Collectors.toList());
    }
}
