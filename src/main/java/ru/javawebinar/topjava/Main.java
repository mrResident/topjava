package ru.javawebinar.topjava;

import ru.javawebinar.topjava.daoimpl.MealMemoryDatasetDao;
import ru.javawebinar.topjava.util.MealsUtil;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {
    public static void main(String[] args) {
        System.out.format("Hello Topjava Enterprise!");
        MealsUtil.MEALS.forEach(MealMemoryDatasetDao.getInstance()::create);
        System.out.println();
        print();
    }

    public static void print() {
        MealMemoryDatasetDao.getInstance().findAll().forEach(System.out::println);
    }
}
