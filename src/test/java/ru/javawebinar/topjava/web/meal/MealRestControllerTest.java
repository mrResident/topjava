package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.util.MealsUtil.createWithExcess;
import static ru.javawebinar.topjava.util.MealsUtil.getWithExcess;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String URL = MealRestController.REST_URL + '/';

    @Autowired
    private MealService service;

    @Test
    void testCreate() throws Exception {
        Meal meal = getCreated();
        ResultActions actions = mockMvc.perform(
            post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(meal)));
        Meal returnMeal = readFromJsonResultActions(actions, Meal.class);
        meal.setId(returnMeal.getId());
        assertMatch(service.getAll(USER_ID), meal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    void testUpdate() throws Exception {
        Meal meal = getUpdated();
        mockMvc.perform(
            put(URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(meal)))
            .andExpect(status().isNoContent());
        assertMatch(service.get(MEAL1_ID, USER_ID), meal);
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(URL + MEAL1_ID))
            .andExpect(status().isNoContent());
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(URL + MEAL1_ID))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Meal.class), MEAL1));
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(URL))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(
                result -> assertThat(readListFromJsonMvcResult(result, MealTo.class))
                    .isEqualTo(getWithExcess(MEALS, USER.getCaloriesPerDay())));
    }

    @Test
    void testGetBetween() throws Exception {
        mockMvc.perform(get(URL + "datebetween?startDateTime=2015-05-30T08:00:00&endDateTime=2015-05-31T10:00:00"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(result -> assertThat(readListFromJsonMvcResult(result, MealTo.class))
                .isEqualTo(List.of(createWithExcess(MEAL4, true), createWithExcess(MEAL1, false))));
    }

}
