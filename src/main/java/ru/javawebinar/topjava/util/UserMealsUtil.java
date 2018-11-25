package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
            new UserMeal(LocalDateTime.of(2018, Month.NOVEMBER, 22, 7, 0), "Завтрак", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new UserMeal(LocalDateTime.of(2018, Month.NOVEMBER, 21, 14, 0), "Обед", 1000),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
            new UserMeal(LocalDateTime.of(2018, Month.NOVEMBER, 22, 14, 0), "Обед", 1200),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new UserMeal(LocalDateTime.of(2018, Month.NOVEMBER, 21, 19, 0), "Ужин", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new UserMeal(LocalDateTime.of(2018, Month.NOVEMBER, 21, 9, 0), "Завтрак", 1200),
            new UserMeal(LocalDateTime.of(2018, Month.DECEMBER, 1, 13, 0), "Обед", 900),
            new UserMeal(LocalDateTime.of(2018, Month.DECEMBER, 1, 18, 0), "Ужин", 1000),
            new UserMeal(LocalDateTime.of(2018, Month.DECEMBER, 2, 7, 30), "Завтрак", 600),
            new UserMeal(LocalDateTime.of(2018, Month.DECEMBER, 2, 12, 30), "Обед", 1100),
            new UserMeal(LocalDateTime.of(2018, Month.DECEMBER, 2, 19, 0), "Ужин", 1200)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
        System.out.println(getFilteredWithExceededExt(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesCountingByDate = mealList.stream()
            .collect(Collectors.groupingBy(
                (um) -> um.getDateTime().toLocalDate(),
                Collectors.summingInt(UserMeal::getCalories)
            ));
        return mealList.stream()
            .filter((um) -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
            .map((um) -> new UserMealWithExceed(
                um.getDateTime(),
                um.getDescription(),
                um.getCalories(),
                caloriesCountingByDate.getOrDefault(um.getDateTime().toLocalDate(), um.getCalories()) > caloriesPerDay))
            .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededExt(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesCountingByDate = new HashMap<>();
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            caloriesCountingByDate.merge(
                userMeal.getDateTime().toLocalDate(),
                userMeal.getCalories(), Integer::sum
            );
        }
        for (UserMeal userMeal : mealList) {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExceeds.add(new UserMealWithExceed(
                    userMeal.getDateTime(),
                    userMeal.getDescription(),
                    userMeal.getCalories(),
                    caloriesCountingByDate.getOrDefault(
                        userMeal.getDateTime().toLocalDate(),
                        userMeal.getCalories()) > caloriesPerDay)
                );
            }
        }
        return userMealWithExceeds;
    }
}
