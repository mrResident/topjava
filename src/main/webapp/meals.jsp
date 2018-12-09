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
        <th align="center">Date</th>
        <th align="center">Description</th>
        <th align="center">Calories</th>
        <th colspan=2 align="center">Action</th>
    </tr>
        <c:forEach items="${mealList}" var="item">
            <tr style=${item.exceed ? "\"color: #FF0000\"" : "\"color: #656665\""}>
                <td>${item.dateTime.format(dtFormatter)}</td>
                <td>${item.description}</td>
                <td>${item.calories}</td>
                <td>
                    <form action = "<c:url value="meals"/>" method="post">
                        <input type="hidden" name="page_caption" value="Edit entry">
                        <input type="hidden" name="id" value="${item.id}">
                        <input type="hidden" name="button_caption" value="Edit">
                        <input type="hidden" name="action" value="edit">
                        <input type="submit" name="edit" value="Edit">
                    </form>
                </td>
                <td>
                    <form action = "<c:url value="meals"/>" method="post">
                        <input type="hidden" name="id" value="${item.id}">
                        <input type="hidden" name="action" value="delete">
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
    <input type="hidden" name="action" value="add">
    <input type="submit" name="add" value="Add new entry to the database">
</form>
</body>
</html>
