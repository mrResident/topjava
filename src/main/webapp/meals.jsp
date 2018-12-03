<%--
  Created by IntelliJ IDEA.
  User: alexandr
  Date: 02.12.18
  Time: 2:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
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
<jsp:useBean id="dtFormatter" scope="request" type="java.time.format.DateTimeFormatter" />

<table>
    <caption>Meals table</caption>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
        <c:forEach items="${mealList}" var="item">
            <tr style=${item.exceed ? "\"color: #FF0000\"" : "\"color: #656665\""}>
                <td>${item.dateTime.format(dtFormatter)}</td>
                <td>${item.description}</td>
                <td>${item.calories}</td>
            </tr>
        </c:forEach>
</table>

</body>
</html>
