package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            try {
                adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
                adminUserController.create(new User(null, "Alexander", "email1@mail.ru", "password", Role.ROLE_ADMIN));
                adminUserController.create(new User(null, "Alexander", "email3@mail.ru", "password", Role.ROLE_ADMIN));
                adminUserController.create(new User(null, "Resident", "email2@mail.ru", "password", Role.ROLE_ADMIN));
                adminUserController.getAll().forEach(System.out::println);
                User user = adminUserController.get(3);
                System.out.println(user);
                System.out.println(adminUserController.getByMail("email2@mail.ru"));
                user.setEmail("resident0007@gmail.com");
                adminUserController.update(user, 3);
                adminUserController.getAll().forEach(System.out::println);
                adminUserController.delete(1);
                System.out.println(adminUserController.getByMail("em@mail.ru"));
            } catch (NotFoundException e) {
                System.err.println(e.getMessage());
            }
            System.out.println("== [mealRestController] Get all meals before input > ==");
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.getAll().forEach(System.out::println);
            System.out.println("== [mealRestController] Set meals for current user and get meal list > ==");
            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 10, 10, 15), "Завтрак", 500));
            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 15, 13, 45), "Обед", 1000));
            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.JUNE, 12, 12, 30), "Обед", 500));
            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.JUNE, 5, 9, 10), "Завтрак", 1000));
            mealRestController.getAll().forEach(System.out::println);
            System.out.println("== [mealRestController] Trying get meal do not belong for current user > ==");
            try {
                System.out.println(mealRestController.get(1));
            } catch (NotFoundException e) {
                System.err.println(e.getMessage());
            }
            System.out.println("== [mealRestController] Trying get and edit meal for current user > ==");
            try {
                Meal meal = mealRestController.get(8);
                System.out.println(meal);
                Meal newMeal = new Meal(meal.getId(), meal.getDateTime(), "Обед 123", 1500);
                mealRestController.update(newMeal, meal.getId());
                System.out.println("-----------");
                mealRestController.getAll().forEach(System.out::println);
            } catch (NotFoundException e) {
                System.err.println(e.getMessage());
            }
            System.out.println("== [mealRestController] Trying set meal do not belong for current user > ==");
            try {
                Meal newMeal = new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 1500);
                mealRestController.update(newMeal, 1);
            } catch (NotFoundException e) {
                System.err.println(e.getMessage());
                mealRestController.getAll().forEach(System.out::println);
            }
            System.out.println("== [mealRestController] Trying delete meal from list for current user > ==");
            try {
                mealRestController.delete(7);
                mealRestController.getAll().forEach(System.out::println);
            } catch (NotFoundException e) {
                System.err.println(e.getMessage());
            }
            System.out.println("== [mealRestController] Trying delete meal from list do not belong for current user > ==");
            try {
                mealRestController.delete(1);
            } catch (NotFoundException e) {
                System.err.println(e.getMessage());
                mealRestController.getAll().forEach(System.out::println);
            }
        }
    }
}
