package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

//    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
//        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
//    }
//
//    public static boolean isBetween(LocalDate ld, LocalDate startDate, LocalDate endDate) {
//        return ld.compareTo(startDate) >= 0 && ld.compareTo(endDate) <= 0;
//    }

    public static <T extends Comparable<? super T>> boolean isBetween(T local, T start, T end) {
        return local.compareTo(start) >= 0 && local.compareTo(end) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
