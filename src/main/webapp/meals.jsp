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
        <th>ID</th>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
        <c:forEach items="${mealList}" var="item">
            <tr style=${item.exceed ? "\"color: #FF0000\"" : "\"color: #656665\""}>
                <td>${item.id}</td>
                <td>${item.dateTime.format(dtFormatter)}</td>
                <td>${item.description}</td>
                <td>${item.calories}</td>
                <td>
                    <form action = "<c:url value="meals"/>" method="post">
                        <input type="hidden" name="page_caption" value="Edit entry with id=${item.id}">
                        <input type="hidden" name="id" value="${item.id}">
                        <input type="hidden" name="date" value="${item.dateTime}">
                        <input type="hidden" name="description" value="${item.description}">
                        <input type="hidden" name="calories" value="${item.calories}">
                        <input type="hidden" name="button_caption" value="Edit">
                        <input type="submit" name="edit" value="Edit">
                    </form>
                </td>
                <td>
                    <form action = "<c:url value="meals"/>" method="post">
                        <input type="hidden" name="id" value="${item.id}">
                        <input type="submit" name="delete" value="Delete">
                    </form>
                </td>
            </tr>
        </c:forEach>
</table>
<br>
<br>
<form action = "<c:url value="meals"/>" method="post">
    <input type="hidden" name="page_caption" value="Add new entry to the database">
    <input type="hidden" name="button_caption" value="Add">
    <input type="submit" name="add" value="Add new entry to the database">
</form>
</body>
</html>
