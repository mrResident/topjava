package ru.javawebinar.topjava.web.formatters;

import org.springframework.format.Formatter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

public class LocalTimeFormatter implements Formatter<LocalTime> {

    @Override
    public LocalTime parse(String text, Locale locale) {
        return parseLocalTime(text);
    }

    @Override
    public String print(LocalTime localTime, Locale locale) {
        return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
