<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h3>Filters</h3>
    <hr/>
    <%--<jsp:useBean id="start_date" type="java.time.LocalDate" scope="request"/>--%>
    <%--<jsp:useBean id="end_date" type="java.time.LocalDate" scope="request"/>--%>
    <%--<jsp:useBean id="start_time" type="java.time.LocalTime" scope="request"/>--%>
    <%--<jsp:useBean id="end_time" type="java.time.LocalTime" scope="request"/>--%>
    <form method="get" action="meals">
        <table border="1" cellpadding="8" cellspacing="1">
            <thead>
            <tr>
                <th>Date filter</th>
                <th>Time filter</th>
            </tr>
            </thead>
            <tr>
                <td><label>Start Date: <input type="date" name="start_date" value="${start_date}"></label></td>
                <td><label>Start Time: <input type="time" name="start_time" value="${start_time}"></label></td>
            </tr>
            <tr>
                <td><label>End Date: <input type="date" name="end_date" value="${end_date}"></label></td>
                <td><label>End Time: <input type="time" name="end_time" value="${end_time}"></label></td>
            </tr>
        </table>
        <br>
        <input type="hidden" name="action" value="set_date_time_filter">
        <button type="submit">Set filter</button>
    </form>
    <a href="meals?action=reset_date_time_filter">Reset filter</a>
    <br>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <hr/>
    <h3>Meals</h3>
    <table border="1" cellpadding="8" cellspacing="0">
        <caption>Meals table</caption>
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>