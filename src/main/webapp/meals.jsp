<%--
  Created by IntelliJ IDEA.
  User: alexandr
  Date: 02.12.18
  Time: 2:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h3><a href="index.html">Go to Home</a></h3>
<h2>Meals</h2>

<jsp:useBean id="mealList" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealWithExceed>" />
<c:set var="dateTimePatternt" value="${DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm\")}" />

<table>
    <caption>Meals table</caption>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
        <c:forEach items="${mealList}" var="item">
            <c:set var = "cellColor" value = "\"color: #656665\""/>
            <c:if test="${item.exceed}">
                <c:set var = "cellColor" value = "\"color: #FF0000\""/>
            </c:if>
            <tr style=${cellColor}>
                <td>${item.dateTime.format(dateTimePatternt)}</td>
                <td>${item.description}</td>
                <td>${item.calories}</td>
            </tr>
        </c:forEach>
</table>

</body>
</html>
